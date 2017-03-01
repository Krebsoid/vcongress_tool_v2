package de.scisertec.event.application.impl.service;

import de.scisertec.event.application.api.model.command.feature.EventParticipationFeatureUpdateCommand;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.service.EventFeatureService;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.event.EventFeatureDeletion;
import de.scisertec.event.domain.model.event.EventParticipationFeatureEditation;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.NotificationFeature;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;
import de.scisertec.event.infrastructure.repository.EventRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class EventFeatureServiceBean implements EventFeatureService {

    @Inject
    EventRepository eventRepository;

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Override
    public EventFeatureRepresentation updateEventFeature(Long eventFeatureId, EventParticipationFeatureUpdateCommand eventParticipationFeatureUpdateCommand) {
        EventFeature eventFeature = eventFeatureRepository.findBy(eventFeatureId);
        if(eventFeature.eventFeatureCategory().equals(EventFeatureCategory.TEXT)) {
            ((NotificationFeature)eventFeature).notification(eventParticipationFeatureUpdateCommand.getLabel());
        }
        eventFeatureRepository.save(eventFeature);
        return eventFeature.getRepresentation();
    }

    @Override
    public EventFeatureRepresentation updateParticipationEventFeature(Long eventFeatureId, EventParticipationFeatureUpdateCommand eventParticipationFeatureUpdateCommand) {
        EventParticipationFeature eventFeature = (EventParticipationFeature) eventFeatureRepository.findBy(eventFeatureId);
        eventFeature.label(eventParticipationFeatureUpdateCommand.getLabel());
        eventFeature.description(eventParticipationFeatureUpdateCommand.getDescription());
        eventFeature.required(eventParticipationFeatureUpdateCommand.getRequired());
        eventFeatureRepository.save(eventFeature);
        EventParticipationFeatureEditation.create(eventFeature);
        return eventFeature.getRepresentation();
    }


    @Override
    public EventFeatureRepresentation removeEventFeature(Long eventFeatureId) {
        EventFeature eventFeature = eventFeatureRepository.findBy(eventFeatureId);
        eventFeature.getEventSelections().clear();
        eventFeatureRepository.saveAndFlushAndRefresh(eventFeature);
        Event event = eventFeature.event();
        event.eventFeatures().remove(eventFeature);
        eventRepository.save(event);
        EventFeatureDeletion.create(eventFeature);
        return eventFeature.getRepresentation();
    }

}
