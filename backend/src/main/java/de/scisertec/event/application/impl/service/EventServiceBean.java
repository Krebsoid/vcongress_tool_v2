package de.scisertec.event.application.impl.service;

import de.scisertec.account.domain.factory.GroupFactory;
import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.Relationship;
import de.scisertec.account.domain.model.Role;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.exception.AccountDisabledException;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.RelationshipRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.infrastructure.qualifier.Active;
import de.scisertec.event.application.api.model.command.EventCreationCommand;
import de.scisertec.event.application.api.model.command.feature.EventFeatureCreationCommand;
import de.scisertec.event.application.api.model.representation.EventDashboardRepresentation;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventRepresentation;
import de.scisertec.event.application.api.model.representation.ParticipationListRepresentation;
import de.scisertec.event.application.api.service.EventService;
import de.scisertec.event.application.impl.model.representation.EventToDashboardRepresentation;
import de.scisertec.event.application.impl.model.representation.EventToRepresentation;
import de.scisertec.event.application.impl.model.representation.ParticipationToListRepresentation;
import de.scisertec.event.domain.model.*;
import de.scisertec.event.domain.model.event.*;
import de.scisertec.event.domain.model.factory.EventFeatureFactory;
import de.scisertec.event.domain.model.feature.*;
import de.scisertec.event.infrastructure.exception.FeatureDuplicateException;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;
import de.scisertec.event.infrastructure.repository.EventRepository;
import de.scisertec.event.infrastructure.repository.ParticipantRepository;
import de.scisertec.payment.application.api.service.PaymentService;
import de.scisertec.payment.domain.event.CustomerPaid;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class EventServiceBean implements EventService {

    @Inject
    EventRepository eventRepository;

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Inject
    ParticipantRepository participantRepository;

    @Inject
    GroupRepository groupRepository;

    @Inject
    RelationshipRepository relationshipRepository;

    @Inject
    GroupFactory groupFactory;

    @Inject
    ConfigurationManager configurationManager;

    @Inject
    @Active
    Instance<User> user;

    @Inject
    javax.enterprise.event.Event<Event> creationEvent;

    @Inject
    PaymentService paymentService;

    @Override
    public EventRepresentation createEvent(EventCreationCommand eventCreationCommand) {
        Event event = new Event();
        event.name(eventCreationCommand.getName()).identifier(eventCreationCommand.getIdentifier());
        event.description(eventCreationCommand.getDescription()).location(eventCreationCommand.getLocation());
        event.startDate(eventCreationCommand.getStartDate());
        event.endDate(eventCreationCommand.getEndDate()).website(eventCreationCommand.getWebsite());
        event.startTime(eventCreationCommand.getStartTime()).endTime(eventCreationCommand.getEndTime());
        event.contact(eventCreationCommand.getContact());
        event.additionalInformation(eventCreationCommand.getAdditionalInformation());

        EventLogo eventLogo = new EventLogo();
        eventLogo.event(event);
        event.eventLogo(eventLogo);

        eventRepository.save(event);

        createParticipantStatusFeature(event);
        createGroupForEvent(event);
        createDisclaimerFeature(event);
        createMailFeatures(event);
        addEventModules(event, eventCreationCommand.getEventModules());
        if(eventCreationCommand.getRegistrationEnd() != null) {
            createRegistrationDeadline(event, eventCreationCommand.getRegistrationEnd());
        } else {
            createRegistrationDeadline(event, eventCreationCommand.getStartDate());
        }
        createIndexNotification(event);

        creationEvent.fire(event);

        EventCreation.create(event);
        return new EventToRepresentation(event);
    }

    @Override
    public EventRepresentation createShortEvent(EventCreationCommand eventCreationCommand) {
        Event event = new Event();
        event.name(eventCreationCommand.getName()).identifier(eventCreationCommand.getIdentifier());
        event.description(eventCreationCommand.getDescription()).location(eventCreationCommand.getLocation());
        event.startDate(eventCreationCommand.getStartDate());
        event.endDate(eventCreationCommand.getEndDate()).website(eventCreationCommand.getWebsite());
        event.startTime(eventCreationCommand.getStartTime()).endTime(eventCreationCommand.getEndTime());
        event.contact(eventCreationCommand.getContact());
        event.additionalInformation(eventCreationCommand.getAdditionalInformation());
        event.shortVersion(true);
        eventRepository.save(event);

        createParticipantStatusFeatureForShortEvent(event, eventCreationCommand.getLimit());
        createGroupForEvent(event);
        createDisclaimerFeature(event);
        createMailFeatures(event);
        addEventModules(event, eventCreationCommand.getEventModules());
        if(eventCreationCommand.getRegistrationEnd() != null) {
            createRegistrationDeadline(event, eventCreationCommand.getRegistrationEnd());
        } else {
            createRegistrationDeadline(event, eventCreationCommand.getStartDate());
        }
        createIndexNotification(event);

        creationEvent.fire(event);

        EventCreation.create(event);
        return new EventToRepresentation(event);
    }

    private void addEventModules(Event event, List<String> eventModules) {
        eventModules.forEach(s -> event.addEventModule(EventModule.valueOf(s)));
    }

    private void createDisclaimerFeature(Event event) {
        DisclaimerFeature disclaimerFeature1 = new DisclaimerFeature();
        disclaimerFeature1.event(event);
        String disclaimer1 = configurationManager.getProperty("disclaimer.text1");
        disclaimerFeature1.mandatory(true).content(disclaimer1).index(1);
        eventFeatureRepository.save(disclaimerFeature1);

        if(!event.shortVersion()) {
            DisclaimerFeature disclaimerFeature2 = new DisclaimerFeature();
            disclaimerFeature2.event(event);
            String disclaimer2 = configurationManager.getProperty("disclaimer.text2");
            disclaimerFeature2.mandatory(true).content(disclaimer2).index(2);
            eventFeatureRepository.save(disclaimerFeature2);
        }

        DisclaimerFeature disclaimerFeature3 = new DisclaimerFeature();
        disclaimerFeature3.event(event);
        String disclaimer3 = configurationManager.getProperty("disclaimer.text3");
        disclaimerFeature3.mandatory(true).content(disclaimer3).index(3);
        eventFeatureRepository.save(disclaimerFeature3);

        DisclaimerFeature disclaimerFeature4 = new DisclaimerFeature();
        disclaimerFeature4.event(event);
        String disclaimer4 = configurationManager.getProperty("disclaimer.text4");
        disclaimerFeature4.mandatory(false).content(disclaimer4).index(4);
        eventFeatureRepository.save(disclaimerFeature4);

        DisclaimerFeature disclaimerFeature5 = new DisclaimerFeature();
        disclaimerFeature5.event(event);
        disclaimerFeature5.mandatory(true).content("").index(5);
        eventFeatureRepository.save(disclaimerFeature5);
    }

    private void createParticipantStatusFeature(Event event) {
        ParticipantStatusFeature participantStatusFeature = new ParticipantStatusFeature();
        participantStatusFeature.event(event);
        participantStatusFeature.eventFeatureType(EventFeatureType.PARTICIPANT_STATUS);
        participantStatusFeature.label("Participant status");
        eventFeatureRepository.save(participantStatusFeature);
    }

    private void createParticipantStatusFeatureForShortEvent(Event event, Integer limit) {
        ParticipantStatusFeature participantStatusFeature = new ParticipantStatusFeature();
        participantStatusFeature.event(event);
        participantStatusFeature.eventFeatureType(EventFeatureType.PARTICIPANT_STATUS);
        participantStatusFeature.label("Participant status");

        EventItem defaultStatus = new EventItem();
        defaultStatus.name("Teilnahme").addEventFlag("DEFAULT_STATUS").addEventFlag("PARTICIPANT_STATUS")
                .participantLimit(limit).eventFeature(participantStatusFeature);
        participantStatusFeature.status().add(defaultStatus);

        eventFeatureRepository.save(participantStatusFeature);
    }

    private void createRegistrationDeadline(Event event, String registrationEnd) {
        DeadlineFeature deadlineFeature = new DeadlineFeature();
        deadlineFeature.event(event);
        deadlineFeature.eventFeatureType(EventFeatureType.REGISTRATION);
        deadlineFeature.deadline(registrationEnd);
        eventFeatureRepository.save(deadlineFeature);
    }

    private void createIndexNotification(Event event) {
        NotificationFeature notificationFeature = new NotificationFeature();
        notificationFeature.eventFeatureType(EventFeatureType.INDEX);
        notificationFeature.event(event);
        eventFeatureRepository.save(notificationFeature);
    }

    private void createMailFeatures(Event event) {
        MailFeature commonMail = new MailFeature();
        commonMail.event(event);
        commonMail.eventFeatureType(EventFeatureType.MAIL_GENERAL);
        eventFeatureRepository.save(commonMail);
        MailFeature registrationMail = new MailFeature();
        registrationMail.event(event);
        registrationMail.eventFeatureType(EventFeatureType.MAIL_REGISTRATION);
        eventFeatureRepository.save(registrationMail);
        MailFeature paymentMail = new MailFeature();
        paymentMail.event(event);
        paymentMail.eventFeatureType(EventFeatureType.MAIL_PAYMENT);
        eventFeatureRepository.save(paymentMail);
        MailFeature creditMail = new MailFeature();
        creditMail.event(event);
        creditMail.eventFeatureType(EventFeatureType.MAIL_CREDIT);
        eventFeatureRepository.save(creditMail);
        MailFeature paymentCompleteMail = new MailFeature();
        paymentCompleteMail.event(event);
        paymentCompleteMail.eventFeatureType(EventFeatureType.MAIL_PAYMENT_COMPLETE);
        eventFeatureRepository.save(paymentCompleteMail);
    }

    private void createGroupForEvent(Event event) {
        Group group = new Group();
        Role participant = Role.create("TEILNEHMER");
        Role staff = Role.create("MITARBEITER");
        Role organizer = Role.create("ORGANISATOR");
        Role reviewer = Role.create("GUTACHTER");
        group.name(event.identifier())
                .identifier(event.identifier())
                .addRole(participant)
                .addRole(staff)
                .addRole(organizer)
                .addRole(reviewer)
                .defaultRole(participant);
        groupRepository.save(group);

        User user = this.user.get();
        user.addRelationship(group, Collections.singletonList(organizer).stream().collect(Collectors.toSet()));
        userRepository.save(user);
    }

    @Override
    public EventRepresentation setTestMode(String identifier, Boolean testMode) {
        Event event = eventRepository.findByIdentifier(identifier);
        if(testMode) {
            EventTestModeOn.create(event);
            event.eventStatus(EventStatus.TESTMODE);
        } else {
            EventTestModeOff.create(event);
            event.eventStatus(EventStatus.CREATED);
            event.participants().clear();
        }
        return new EventToRepresentation(event);
    }

    @Override
    public EventRepresentation setLicense(String identifier, String license) {
        Event event = eventRepository.findByIdentifier(identifier);
        event.eventLicense(EventLicense.valueOf(license));
        eventRepository.save(event);
        EventLicenseSet.create(event, license);
        return new EventToRepresentation(event);
    }

    @Override
    public EventRepresentation addModule(String identifier, String module) {
        Event event = eventRepository.findByIdentifier(identifier);
        event.eventModules().add(EventModule.valueOf(module));
        EventModuleAdd.create(event, module);
        return new EventToRepresentation(event);
    }

    @Override
    public EventRepresentation removeModule(String identifier, String module) {
        Event event = eventRepository.findByIdentifier(identifier);
        event.eventModules().remove(EventModule.valueOf(module));
        EventModuleRemove.create(event, module);
        return new EventToRepresentation(event);
    }

    @Override
    public EventRepresentation editEvent(String identifier, EventCreationCommand eventCreationCommand) {
        Event event = eventRepository.findByIdentifier(identifier);
        event.name(eventCreationCommand.getName()).identifier(eventCreationCommand.getIdentifier());
        event.description(eventCreationCommand.getDescription());
        User user = this.user.get();
        if(user != null && user.hasRole("system", "ADMIN")) {
            event.startDate(eventCreationCommand.getStartDate()).endDate(eventCreationCommand.getEndDate());
            event.location(eventCreationCommand.getLocation());
        }
        event.website(eventCreationCommand.getWebsite());
        event.startTime(eventCreationCommand.getStartTime()).endTime(eventCreationCommand.getEndTime());
        event.contact(eventCreationCommand.getContact());
        event.additionalInformation(eventCreationCommand.getAdditionalInformation());

        if(eventCreationCommand.getRegistrationEnd() != null) {
            updateDeadlineFeature(event, eventCreationCommand.getRegistrationEnd());
        } else {
            updateDeadlineFeature(event, eventCreationCommand.getStartDate());
        }

        eventRepository.save(event);
        EventUpdate.create(event);
        return new EventToRepresentation(event);
    }

    private void updateDeadlineFeature(Event event, String registrationEnd) {
        Optional<EventFeature> registrationDeadline = event.eventFeatures(EventFeatureCategory.DEADLINE, EventFeatureType.REGISTRATION).stream().findFirst();
        if(registrationDeadline.isPresent()) {
            ((DeadlineFeature)registrationDeadline.get()).deadline(registrationEnd);
        } else {
            createRegistrationDeadline(event, registrationEnd);
        }
    }

    @Inject
    UserRepository userRepository;

    @Override
    public void deleteEvent(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        event.participants().clear();
        EventDeletion.create(event);
        eventRepository.saveAndFlushAndRefresh(event);
        eventRepository.remove(event);
        Group group = groupRepository.findByIdentifier(event.identifier());
        List<User> users = userRepository.findByGroup(group);
        users.stream().forEach(user -> {
            Optional<Relationship> foundRelationship = user.relationships().stream().filter(relationship -> relationship.group().equals(group)).findFirst();
            if(foundRelationship.isPresent()) {
                user.relationships().remove(foundRelationship.get());
                userRepository.saveAndFlushAndRefresh(user);
            }
        });
        group.relationships().forEach(relationship -> relationshipRepository.remove(relationship));
        group.roles().clear();
        groupRepository.remove(group);
    }

    @Inject
    javax.enterprise.event.Event<EventToRepresentation> event;

    @Override
    public EventRepresentation getEvent(String identifier) {
        EventToRepresentation representation = new EventToRepresentation(eventRepository.findByIdentifier(identifier), this.user.get());
        event.fire(representation);
        return representation;
    }

    private void onEventRegistration(@Observes EventSelectionSave eventSelectionSave) {
        Event event = eventSelectionSave.eventSelection().participation().event();
        checkForFull(event);
    }

    private void onShortEventRegistration(@Observes PersonEventRegistration personEventRegistration) {
        Event event = personEventRegistration.event;
        if(event.shortVersion()) {
            checkForFull(event);
        }
    }

    private void checkForFull(Event event) {
        if(event.isFull()) {
            event.eventStatus(EventStatus.CLOSED);
            NotificationFeature eventFeature = (NotificationFeature)event.eventFeatures(EventFeatureCategory.TEXT, EventFeatureType.INDEX).stream().findFirst().get();
            eventFeature.notification("Diese Veranstaltung ist bereits ausgebucht, die maximale Teilnehmerzahl ist erreicht.");
            eventFeatureRepository.save(eventFeature);
        }
    }

    @Override
    public List<EventDashboardRepresentation> getAllEventAsSummary() {
        Comparator<EventDashboardRepresentation> comparator = Comparator.comparing(EventDashboardRepresentation::getStatus).reversed();
        comparator = comparator.thenComparing(Comparator.comparing(EventDashboardRepresentation::getRegistrationEnd));
        List<EventDashboardRepresentation> events = eventRepository.findAllActive().stream()
                .map(EventToDashboardRepresentation::new).collect(Collectors.toList());
        return events.stream()
                .filter(event -> user.get().hasGroup(event.getIdentifier()) || user.get().hasRole("system", "ADMIN") || user.get().hasRole("system", "ORGANISATION"))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventRepresentation> getAllEvents() {
        return eventRepository.findAll().stream().map(EventToRepresentation::new).collect(Collectors.toList());
    }

    @Override
    public List<ParticipationListRepresentation> getParticipants(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        List<Participation> participations = participantRepository.findAllByEvent(event);
        return participations.stream().map(ParticipationToListRepresentation::new).collect(Collectors.toList());
    }

    @Override
    public EventRepresentation publishEvent(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        User user = this.user.get();
        if(user == null || !user.enabled()) {
            throw new AccountDisabledException();
        }
        if(event.eventStatus().equals(EventStatus.CREATED) || event.eventStatus().equals(EventStatus.TESTMODE)) {
            EventTestModeOff.create(event);
            event.eventStatus(EventStatus.PUBLISHED);
            event.participants().clear();
        }
        eventRepository.save(event);
        EventPublish.create(event);
        return new EventToRepresentation(event);
    }

    public void onPaidViaPayPal(@Observes CustomerPaid customerPaid) {
        publishEvent(customerPaid.eventIdentifier());
    }

    @Override
    public EventRepresentation closeEvent(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        if(event.eventStatus().equals(EventStatus.PUBLISHED)) {
            event.eventStatus(EventStatus.CLOSED);
        }
        eventRepository.save(event);
        EventPublish.create(event);
        return new EventToRepresentation(event);
    }

    @Override
    public EventRepresentation reopenEvent(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        if(event.eventStatus().equals(EventStatus.CLOSED)) {
            event.eventStatus(EventStatus.PUBLISHED);
        }
        eventRepository.save(event);
        EventPublish.create(event);
        return new EventToRepresentation(event);
    }

    @Override
    public EventRepresentation archiveEvent(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        if(event.eventStatus().equals(EventStatus.CLOSED)) {
            event.eventStatus(EventStatus.ARCHIVED);
        }
        eventRepository.save(event);
        EventArchived.create(event);
        return new EventToRepresentation(event);
    }

    @Inject
    javax.enterprise.event.Event<EventFeature> eventFeatureEvent;

    @Override
    public EventFeatureRepresentation addEventFeature(String identifier, EventFeatureCreationCommand eventFeatureCreationCommand) {
        Event event = eventRepository.findByIdentifier(identifier);
        EventFeature existingEventFeature = event.eventFeatures(EventFeatureType.valueOf(eventFeatureCreationCommand.getFeatureType()));
        if(existingEventFeature != null) {
            if(existingEventFeature.singleFeature()) {
                throw new FeatureDuplicateException();
            }
        }
        EventFeature eventFeature = EventFeatureFactory.create(EventFeatureType.valueOf(eventFeatureCreationCommand.getFeatureType()));
        if (eventFeature != null) {
            eventFeature.event(event);
            eventFeature.init();
        }
        eventFeatureRepository.save(eventFeature);
        eventFeatureEvent.fire(eventFeature);
        EventFeatureCreation.create(eventFeature);
        return eventFeature.getRepresentation();
    }

}
