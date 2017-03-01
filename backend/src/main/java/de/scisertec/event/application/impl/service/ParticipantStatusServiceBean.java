package de.scisertec.event.application.impl.service;

import de.scisertec.core.application.impl.helper.CalendarHelper;
import de.scisertec.event.application.api.model.command.EventFlagCreationCommand;
import de.scisertec.event.application.api.model.command.ParticipantStatusCreationCommand;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.service.ParticipantStatusService;
import de.scisertec.event.application.impl.model.representation.EventItemToRepresentation;
import de.scisertec.event.domain.model.*;
import de.scisertec.event.domain.model.event.EventSelectionDeletion;
import de.scisertec.event.domain.model.event.ParticipantStatusCreation;
import de.scisertec.event.domain.model.event.ParticipantStatusDeletion;
import de.scisertec.event.domain.model.event.ParticipantStatusEditation;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.selection.ParticipantStatusSelection;
import de.scisertec.event.infrastructure.exception.StatusSelectedOnDeletionException;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;
import de.scisertec.event.infrastructure.repository.EventItemRepository;
import de.scisertec.event.infrastructure.repository.EventSelectionRepository;
import org.joda.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@ApplicationScoped
public class ParticipantStatusServiceBean implements ParticipantStatusService {

    @Inject
    EventItemRepository eventItemRepository;

    @Inject
    EventSelectionRepository eventSelectionRepository;

    @Inject
    EventFeatureRepository eventFeatureRepository;


    @Override
    public List<EventItemRepresentation> getAllStatus(Long eventFeatureId) {
        EventFeature eventFeature = eventFeatureRepository.findBy(eventFeatureId);
        return eventItemRepository.findByEventFeature(eventFeature).stream()
                .map(EventItemToRepresentation::new).collect(Collectors.toList());
    }

    @Override
    public EventItemRepresentation getStatus(Long eventFeatureId, Long participantStatusId) {
        return new EventItemToRepresentation(eventItemRepository.findBy(participantStatusId));
    }

    @Override
    public EventItemRepresentation addStatus(Long eventFeatureId, ParticipantStatusCreationCommand participantStatusCreationCommand) {
        EventFeature eventFeature = eventFeatureRepository.findBy(eventFeatureId);
        EventItem participantStatus = new EventItem();
        participantStatus.name(participantStatusCreationCommand.getName())
                .participantLimit(participantStatusCreationCommand.getLimit())
                .cost(participantStatusCreationCommand.getCost())
                .tax(participantStatusCreationCommand.getTax())
                .addEventFlag("PARTICIPANT_STATUS")
                .eventFeature(eventFeature)
                .eventItemType(EventItemType.PARTICIPANT_STATUS);
        if(participantStatusCreationCommand.getEarlyBirdActive()) {
            if(participantStatusCreationCommand.getEarlyBird()) {
                participantStatus.startDate(participantStatusCreationCommand.getStartDate());
                participantStatus.endDate(participantStatusCreationCommand.getEndDate());
                participantStatus.addEventFlag("EARLY_BIRD");
            } else {
                Calendar newStart = CalendarHelper.stringToCalendar(participantStatusCreationCommand.getEndDate());
                if (newStart != null) {
                    newStart.add(Calendar.DATE, 1);
                    participantStatus.startDate(newStart);
                    participantStatus.removeEventFlag("EARLY_BIRD");
                }
            }
        }
        eventItemRepository.save(participantStatus);
        ParticipantStatusCreation.create(participantStatus);
        return new EventItemToRepresentation(participantStatus);
    }

    @Override
    public EventItemRepresentation addFlag(Long participantStatusId, EventFlagCreationCommand eventFlagCreationCommand) {
        EventItem participantStatus = eventItemRepository.findBy(participantStatusId);
        participantStatus.addEventFlag(eventFlagCreationCommand.getName());
        eventItemRepository.save(participantStatus);
        return new EventItemToRepresentation(participantStatus);
    }

    @Override
    public EventItemRepresentation removeFlag(Long participantStatusId, Long flagId) {
        EventItem participantStatus = eventItemRepository.findBy(participantStatusId);
        participantStatus.removeEventFlag(flagId);
        eventItemRepository.save(participantStatus);
        return new EventItemToRepresentation(participantStatus);
    }

    @Override
    public EventItemRepresentation editStatus(Long eventFeatureId, Long participantStatusId, ParticipantStatusCreationCommand participantStatusUpdateCommand) {
        EventItem participantStatus = eventItemRepository.findBy(participantStatusId);
        participantStatus.name(participantStatusUpdateCommand.getName())
                .participantLimit(participantStatusUpdateCommand.getLimit())
                .cost(participantStatusUpdateCommand.getCost())
                .tax(participantStatusUpdateCommand.getTax());
        if(participantStatusUpdateCommand.getEarlyBirdActive()) {
            if(participantStatusUpdateCommand.getEarlyBird()) {
                participantStatus.startDate(participantStatusUpdateCommand.getStartDate());
                participantStatus.endDate(participantStatusUpdateCommand.getEndDate());
                participantStatus.addEventFlag("EARLY_BIRD");
            } else {
                Calendar newStart = CalendarHelper.stringToCalendar(participantStatusUpdateCommand.getEndDate());
                if (newStart != null) {
                    newStart.add(Calendar.DATE, 1);
                    participantStatus.startDate(newStart);
                    participantStatus.noEndDate();
                    participantStatus.removeEventFlag("EARLY_BIRD");
                }
            }
        } else {
            participantStatus.noStartDate();
            participantStatus.noEndDate();
            participantStatus.removeEventFlag("EARLY_BIRD");
        }
        eventItemRepository.save(participantStatus);
        ParticipantStatusEditation.create(participantStatus);
        return new EventItemToRepresentation(participantStatus);
    }

    @Override
    public EventItemRepresentation removeStatus(Long eventFeatureId, Long participantStatusId) {
        EventItem participantStatus = eventItemRepository.findBy(participantStatusId);
        EventFeature eventFeature = eventFeatureRepository.findBy(eventFeatureId);
        eventFeature.getEventSelections().forEach(eventSelection -> {
            if(eventSelection.isSelected(participantStatus)) {
                ((ParticipantStatusSelection)eventSelection).eventItem(null);
            }
        });
        eventFeatureRepository.saveAndFlushAndRefresh(eventFeature);
        eventItemRepository.removeAndFlush(participantStatus);
        ParticipantStatusDeletion.create(participantStatus);
        return new EventItemToRepresentation(participantStatus);
    }
}
