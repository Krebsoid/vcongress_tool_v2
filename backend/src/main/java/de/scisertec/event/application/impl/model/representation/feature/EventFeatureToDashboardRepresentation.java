package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.feature.EventFeatureDashboardRepresentation;
import de.scisertec.event.application.api.model.representation.feature.EventItemDashboardRepresentation;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.feature.DinnerComboFeature;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.feature.ParticipantStatusFeature;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventFeatureToDashboardRepresentation extends AbstractRepresentation implements EventFeatureDashboardRepresentation {

    String label;
    List<EventItemDashboardRepresentation> eventItems = new ArrayList<>();

    public EventFeatureToDashboardRepresentation(Event event, EventParticipationFeature eventParticipationFeature) {
        this.label = eventParticipationFeature.label();
        if(eventParticipationFeature.eventFeatureType().equals(EventFeatureType.PARTICIPANT_STATUS) || eventParticipationFeature.eventFeatureType().equals(EventFeatureType.SELECTION)) {
            ParticipantStatusFeature participantStatusFeature = (ParticipantStatusFeature) eventParticipationFeature;
            participantStatusFeature.status().stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId())).forEach(eventItem -> {
                Long actual = event.participants().stream()
                        .filter(Participation::active)
                        .filter(participation -> participation.hasEventSelection(eventItem)).count();
                Integer max = eventItem.participantLimit();
                eventItems.add(new EventItemToDashboardRepresentation(eventItem.name(), max, actual.intValue()));
            });
        }
        if(eventParticipationFeature.eventFeatureType().equals(EventFeatureType.DINNER_COMBO)) {
            DinnerComboFeature dinnerComboFeature = (DinnerComboFeature) eventParticipationFeature;
            dinnerComboFeature.dinnerFeatures().stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId())).forEach(dinnerFeature -> {
                Long actual = event.participants().stream()
                        .filter(Participation::active)
                        .filter(participation -> participation.hasEventSelection(dinnerFeature.eventItem())).count();
                Integer max = dinnerFeature.eventItem().participantLimit();
                eventItems.add(new EventItemToDashboardRepresentation(dinnerFeature.eventItem().name(), max, actual.intValue()));
            });
        }
        eventItems = eventItems.stream().map(item ->
                    new EventItemToDashboardRepresentation(item.getName(),
                            item.getMax(),
                            eventItems
                                    .stream()
                                    .filter(innerItem -> innerItem.getName().equals(item.getName()))
                                    .reduce(0, (i, i2) -> i + i2.getActual(), Integer::sum)))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public List<EventItemDashboardRepresentation> getEventItems() {
        return eventItems;
    }

}
