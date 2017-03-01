package de.scisertec.event.application.impl.service;

import de.scisertec.event.application.api.model.command.EventItemCreationCommand;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.service.DinnerService;
import de.scisertec.event.application.impl.model.representation.EventItemToRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.DinnerComboFeatureToRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.DinnerFeatureToRepresentation;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.event.EventParticipationFeatureEditation;
import de.scisertec.event.domain.model.feature.DinnerComboFeature;
import de.scisertec.event.domain.model.feature.DinnerFeature;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.selection.DinnerSelection;
import de.scisertec.event.infrastructure.exception.StatusSelectedOnDeletionException;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class DinnerServiceBean implements DinnerService {

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Inject
    Event<EventFeature> eventFeatureEvent;

    public void onEditation(@Observes EventParticipationFeatureEditation eventParticipationFeatureEditation) {
        if(eventParticipationFeatureEditation.eventFeature().eventFeatureType().equals(EventFeatureType.DINNER_COMBO)) {
            DinnerComboFeature eventFeature = (DinnerComboFeature) eventParticipationFeatureEditation.eventFeature();
            eventFeature.dinnerFeatures().stream().forEach(dinnerFeature -> dinnerFeature.label(eventFeature.label()));
            eventFeatureRepository.save(eventFeature);
        }
    }

    @Override
    public EventFeatureRepresentation createDinner(Long eventFeatureId, EventItemCreationCommand eventItemCreationCommand) {
        DinnerComboFeature dinnerComboFeature = (DinnerComboFeature) eventFeatureRepository.findBy(eventFeatureId);
        DinnerFeature dinnerFeature = new DinnerFeature();
        dinnerFeature.init();
        dinnerFeature.event(dinnerComboFeature.event());
        dinnerFeature.label(dinnerComboFeature.label());
        dinnerFeature.eventItem()
                .name(eventItemCreationCommand.getName())
                .description(eventItemCreationCommand.getDescription())
                .participantLimit(eventItemCreationCommand.getLimit())
                .cost(eventItemCreationCommand.getCost())
                .tax(eventItemCreationCommand.getTax());
        dinnerComboFeature.dinnerFeatures().add(dinnerFeature);
        eventFeatureRepository.save(dinnerFeature);
        eventFeatureEvent.fire(dinnerFeature);
        return new DinnerComboFeatureToRepresentation(dinnerComboFeature);
    }

    @Override
    public EventFeatureRepresentation removeDinner(Long eventFeatureId, Long dinnerId) {
        DinnerComboFeature dinnerComboFeature = (DinnerComboFeature) eventFeatureRepository.findBy(eventFeatureId);
        DinnerFeature dinnerFeature = (DinnerFeature) eventFeatureRepository.findBy(dinnerId);
        dinnerFeature.getEventSelections().clear();
        eventFeatureRepository.saveAndFlushAndRefresh(dinnerFeature);
        dinnerComboFeature.dinnerFeatures().remove(dinnerFeature);
        eventFeatureRepository.save(dinnerComboFeature);
        return new DinnerComboFeatureToRepresentation(dinnerComboFeature);
    }

    @Override
    public EventItemRepresentation getItem(Long eventFeatureId) {
        DinnerFeature eventFeature = (DinnerFeature) eventFeatureRepository.findBy(eventFeatureId);
        return new EventItemToRepresentation(eventFeature.eventItem());
    }

    @Override
    public EventItemRepresentation editItem(Long eventFeatureId, EventItemCreationCommand dinnerCreationCommand) {
        DinnerFeature eventFeature = (DinnerFeature) eventFeatureRepository.findBy(eventFeatureId);
        eventFeature.label(dinnerCreationCommand.getName());
        eventFeature.eventItem()
                .name(dinnerCreationCommand.getName())
                .description(dinnerCreationCommand.getDescription())
                .participantLimit(dinnerCreationCommand.getLimit())
                .cost(dinnerCreationCommand.getCost())
                .tax(dinnerCreationCommand.getTax());
        eventFeatureRepository.save(eventFeature);
        return new EventItemToRepresentation(eventFeature.eventItem());
    }
}
