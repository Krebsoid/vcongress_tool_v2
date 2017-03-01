package de.scisertec.event.application.impl.service;

import de.scisertec.account.application.impl.service.authorization.LoginService;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.event.application.api.model.command.EventRegistrationCommand;
import de.scisertec.event.application.api.model.command.ShortEventRegistrationCommand;
import de.scisertec.event.application.api.service.EventRegistration;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.event.PersonEventRegistration;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.infrastructure.exception.DeadlineExpiredException;
import de.scisertec.event.infrastructure.exception.TestmodeCapacityExceededException;
import de.scisertec.event.infrastructure.repository.EventRepository;
import de.scisertec.event.infrastructure.repository.ParticipantRepository;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.application.impl.model.representation.PersonToRepresentation;
import de.scisertec.person.domain.factory.PersonFactory;
import de.scisertec.person.domain.model.Gender;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.infrastructure.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class EventRegistrationBean implements EventRegistration {

    @Inject
    EventRepository eventRepository;

    @Inject
    PersonFactory personFactory;

    @Inject
    PersonRepository personRepository;

    @Inject
    ParticipantRepository participantRepository;

    @Inject
    GroupRepository groupRepository;

    @Inject
    LoginService loginService;

    @Override
    public PersonRepresentation registerForEvent(EventRegistrationCommand c) {
        Event event = eventRepository.findByIdentifier(c.getGroup());
        if(event.isTestmodeCapacityExceeded()) {
            throw new TestmodeCapacityExceededException();
        }
        if(!event.isDeadlineExpired(EventFeatureType.REGISTRATION)) {
            Person person = personFactory.create(c.getEmail(), c.getPassword(), c.getTitle(),
                    c.getFirstName(), c.getLastName(), Gender.valueOf(c.getGender()), c.getStreet(),
                    c.getZipCode(), c.getCity(), c.getState(), c.getCountry(),
                    c.getInvoiceFullName(), c.getInvoiceInstitute(), c.getInvoiceStreet(), c.getInvoiceZipCode(), c.getInvoiceCity(),
                    c.getInvoiceState(), c.getInvoiceCountry(),
                    c.getPhone(), c.getFax(),
                    c.getInstitute(), c.getDepartment(), c.getPosition(),
                    c.getNotificationRequest());
            person.user().addRelationship(groupRepository.findByIdentifier(c.getGroup()));
            personRepository.save(person);
            Participation participation = Participation.create(person, event);
            event.eventFeatures(EventFeatureCategory.PARTICIPATION).forEach(eventFeature ->
                    {
                        if (((EventParticipationFeature) eventFeature).hasDefaultSelection()) {
                            participation.eventSelections().put(eventFeature.getId(), ((EventParticipationFeature) eventFeature).defaultSelection()
                                    .participation(participation));
                        }
                    }
            );
            loginService.login(person.user(), person.user().credential().password(), event.identifier());
            participantRepository.save(participation);
            PersonEventRegistration.create(participation);
            return new PersonToRepresentation(person);
        } else {
            throw new DeadlineExpiredException();
        }
    }

    @Override
    public PersonRepresentation registerForShortEvent(ShortEventRegistrationCommand c) {
        Event event = eventRepository.findByIdentifier(c.getGroup());
        if(event.isTestmodeCapacityExceeded()) {
            throw new TestmodeCapacityExceededException();
        }
        if(!event.isDeadlineExpired(EventFeatureType.REGISTRATION)) {
            Person person = personFactory.create(c.getEmail(), c.getTitle(),
                    c.getFirstName(), c.getLastName(), Gender.valueOf(c.getGender()), c.getCity(),
                    c.getInstitute(), c.getNotificationRequest());
            person.user().addRelationship(groupRepository.findByIdentifier(c.getGroup()));
            personRepository.save(person);
            Participation participation = Participation.create(person, event);
            event.eventFeatures(EventFeatureCategory.PARTICIPATION).forEach(eventFeature ->
                    {
                        if (((EventParticipationFeature) eventFeature).hasDefaultSelection()) {
                            participation.eventSelections().put(eventFeature.getId(), ((EventParticipationFeature) eventFeature).defaultSelection()
                                    .participation(participation));
                        }
                    }
            );
            participantRepository.save(participation);
            PersonEventRegistration.create(participation);
            return new PersonToRepresentation(person);
        } else {
            throw new DeadlineExpiredException();
        }
    }
}
