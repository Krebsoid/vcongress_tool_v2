package de.scisertec.event.application.impl.service;

import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.exception.NotLoggedInException;
import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.core.infrastructure.qualifier.Active;
import de.scisertec.event.application.api.model.command.ParticipantNoteUpdateCommand;
import de.scisertec.event.application.api.model.command.eventitem.DinnerSaveCommand;
import de.scisertec.event.application.api.model.command.eventitem.ParticipationStatusSaveCommand;
import de.scisertec.event.application.api.model.representation.EventRepresentation;
import de.scisertec.event.application.api.model.representation.ParticipationRepresentation;
import de.scisertec.event.application.api.service.ParticipationService;
import de.scisertec.event.application.impl.model.representation.ParticipationToRepresentation;
import de.scisertec.event.domain.model.*;
import de.scisertec.event.domain.model.event.*;
import de.scisertec.event.domain.model.feature.DinnerFeature;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.selection.DinnerSelection;
import de.scisertec.event.domain.model.selection.ParticipantStatusSelection;
import de.scisertec.event.infrastructure.exception.ConcurrentParticipationSaveException;
import de.scisertec.event.infrastructure.exception.FixedSelectionManipulationException;
import de.scisertec.event.infrastructure.exception.IncompleteParticipationException;
import de.scisertec.event.infrastructure.repository.*;
import de.scisertec.payment.domain.event.TransactionCancel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class ParticipationServiceBean implements ParticipationService, Service {

    @Inject
    EventRepository eventRepository;

    @Inject
    ParticipantRepository participantRepository;

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Inject
    EventItemRepository eventItemRepository;

    @Inject
    EventSelectionRepository eventSelectionRepository;

    @Inject
    @Active
    Instance<User> user;

    public void onEventRepresentation(@Observes EventRepresentation eventRepresentation) {
        User user = this.user.get();
        if(user.isUser()) {
            Participation participation = participantRepository.findByUser(user);
            if(user.hasRole(eventRepresentation.getIdentifier(), "TEILNEHMER")) {
                eventRepresentation.makeParticipationDependant(participation);
            }
        }
    }

    public void onParticipationRepresentation(@Observes ParticipationRepresentation participationRepresentation) {
        User user = this.user.get();
        if(user.isUser()) {
            Participation participation = participantRepository.findByUser(user);
            if(user.hasRole(participationRepresentation.getEventIdentifier(), "TEILNEHMER")) {
                participationRepresentation.makeParticipationDependant(participation);
            }
        }
    }

    public void onEventFeatureCreation(@Observes EventFeature eventFeature) {
        Event event = eventFeature.event();
        if(eventFeature.eventFeatureCategory().equals(EventFeatureCategory.PARTICIPATION)) {
            event.participants().stream().forEach(participation -> {
                        participation.eventSelections().put(eventFeature.getId(), ((EventParticipationFeature) eventFeature).defaultSelection()
                                .participation(participation));
                        participantRepository.save(participation);
                    }
            );
        }
    }

    public void onEventSelectionFixed(@Observes EventSelectionsFixed eventSelectionsFixed) {
        eventSelectionsFixed.fixedFeatureList().entrySet().forEach(eventFeatureId ->
                eventSelectionsFixed.participation().eventSelections().get(eventFeatureId.getKey()).fixed(eventSelectionsFixed));
        participantRepository.save(eventSelectionsFixed.participation());
    }

    public void onEventSelectionReleased(@Observes EventSelectionsReleased eventSelectionsReleased) {
        eventSelectionsReleased.fixedFeatureList().entrySet().forEach(eventFeatureId ->
                eventSelectionsReleased.participation().eventSelections().get(eventFeatureId.getKey()).fixed(eventSelectionsReleased));
        participantRepository.save(eventSelectionsReleased.participation());
    }

    public void onTransactionCancel(@Observes TransactionCancel transactionCancel) {
        Participation participation = transactionCancel.newTransaction().participation();
        participation.eventSelections()
            .forEach((aLong, eventSelection) -> {
                if(transactionCancel.newTransaction().paymentItems().containsKey(aLong)) {
                    if(eventSelection.eventFeature().eventFeatureType().equals(EventFeatureType.DINNER)) {
                        ((DinnerSelection)eventSelection).checked(Boolean.FALSE);
                    }
                    if(eventSelection.eventFeature().eventFeatureType().equals(EventFeatureType.PARTICIPANT_STATUS)
                            || eventSelection.eventFeature().eventFeatureType().equals(EventFeatureType.SELECTION)) {
                        ((ParticipantStatusSelection)eventSelection).eventItem(null);
                    }
                }
            });
        participantRepository.save(participation);
    }

    @Inject
    javax.enterprise.event.Event<ParticipationToRepresentation> event;

    @Override
    public ParticipationRepresentation getParticipation() {
        Participation participation = assertLoggedIn();
        ParticipationToRepresentation participationRepresentation = new ParticipationToRepresentation(participation);
        event.fire(participationRepresentation);
        return participationRepresentation;
    }

    @Override
    public void deleteParticipation(Long id) {
        Participation participation = participantRepository.findBy(id);
        participantRepository.remove(participation);
    }

    @Override
    public ParticipationRepresentation addStatus(ParticipationStatusSaveCommand participationStatusSaveCommand) {
        EventItem eventItem = eventItemRepository.findBy(participationStatusSaveCommand.getEventItemId());
        Participation participation = assertLoggedIn();

        ParticipantStatusSelection eventSelection = (ParticipantStatusSelection)participation.eventSelections().get(participationStatusSaveCommand.getEventFeatureId());

        if(eventSelection.fixed()) {
            throw new FixedSelectionManipulationException();
        }

        if(eventSelection.eventItem() != null && !eventSelection.eventItem().equals(eventItem)) {
            if(!eventItem.isAvailable(participation)) {
                throw new ConcurrentParticipationSaveException();
            }
        }

        if(eventSelection.eventItem() == eventItem) {
            eventSelection.eventItem(null);
        } else {
            eventSelection.eventItem(eventItem);
        }

        participantRepository.save(participation);
        ParticipantStatusSave.create(eventSelection);
        return new ParticipationToRepresentation(participation);
    }

    @Override
    public ParticipationRepresentation addDinner(DinnerSaveCommand dinnerSaveCommand) {
        DinnerFeature eventFeature = (DinnerFeature) eventFeatureRepository.findBy(dinnerSaveCommand.getEventFeatureId());
        Participation participation = assertLoggedIn();

        if(dinnerSaveCommand.getChecked()) {
            if(!eventFeature.eventItem().isAvailable(participation)) {
                throw new ConcurrentParticipationSaveException();
            }
        }

        DinnerSelection dinnerSelection = (DinnerSelection)participation.eventSelections().get(eventFeature.getId());

        if(dinnerSelection.fixed()) {
            throw new FixedSelectionManipulationException();
        }

        dinnerSelection.checked(dinnerSaveCommand.getChecked());
        participantRepository.save(participation);
        DinnerSave.create(dinnerSelection);
        return new ParticipationToRepresentation(participation);
    }

    @Override
    public ParticipationRepresentation getParticipation(Long id) {
        Participation participation = participantRepository.findBy(id);
        return new ParticipationToRepresentation(participation);
    }

    @Override
    public ParticipationRepresentation addStatus(Long id, ParticipationStatusSaveCommand participationStatusSaveCommand) {
        EventItem eventItem = eventItemRepository.findBy(participationStatusSaveCommand.getEventItemId());
        EventFeature eventFeature = eventFeatureRepository.findBy(participationStatusSaveCommand.getEventFeatureId());
        Participation participation = participantRepository.findBy(id);
        ParticipantStatusSelection eventSelection = (ParticipantStatusSelection)participation.eventSelections().get(eventFeature.getId());
        if(eventSelection.fixed()) {
            throw new FixedSelectionManipulationException();
        }
        if(eventSelection.eventItem() == eventItem) {
            eventSelection.eventItem(null);
        } else {
            eventSelection.eventItem(eventItem);
        }
        participantRepository.save(participation);
        ParticipantStatusSave.create(eventSelection);
        return new ParticipationToRepresentation(participation);
    }

    @Override
    public ParticipationRepresentation addDinner(Long id, DinnerSaveCommand dinnerSaveCommand) {
        DinnerFeature eventFeature = (DinnerFeature) eventFeatureRepository.findBy(dinnerSaveCommand.getEventFeatureId());
        Participation participation = participantRepository.findBy(id);
        DinnerSelection dinnerSelection = (DinnerSelection)participation.eventSelections().get(eventFeature.getId());
        if(dinnerSelection.fixed()) {
            throw new FixedSelectionManipulationException();
        }
        dinnerSelection.checked(dinnerSaveCommand.getChecked());
        participantRepository.save(participation);
        DinnerSave.create(dinnerSelection);
        return new ParticipationToRepresentation(participation);
    }

    @Override
    public ParticipationRepresentation cancelParticipation(Long id) {
        Participation participation = participantRepository.findBy(id);
        participation.active(false);
        participantRepository.save(participation);
        return new ParticipationToRepresentation(participation);
    }

    @Override
    public ParticipationRepresentation enableParticipation(Long id) {
        Participation participation = participantRepository.findBy(id);
        participation.active(true);
        participantRepository.save(participation);
        return new ParticipationToRepresentation(participation);
    }

    @Override
    public ParticipationRepresentation save(Long id, String method) {
        Participation participation = participantRepository.findBy(id);
        if(participation.complete()) {
            ParticipationSave.create(participation.event(), participation.person(), method);
            return new ParticipationToRepresentation(participation);
        } else {
            throw new IncompleteParticipationException();
        }
    }

    @Override
    public ParticipationRepresentation updateNote(Long id, ParticipantNoteUpdateCommand participantNoteUpdateCommand) {
        Participation participation = participantRepository.findBy(id);
        participation.note(participantNoteUpdateCommand.getNote());
        participantRepository.save(participation);
        return new ParticipationToRepresentation(participation);
    }

    private Participation assertLoggedIn() {
        if(user.get().isUser()) {
            return participantRepository.findByUser(user.get());
        } else {
            throw new NotLoggedInException();
        }
    }
}
