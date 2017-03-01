package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Participation;

import java.util.Map;
import java.util.stream.Collectors;

public class EventSelectionsReleased extends DomainEvent<EventSelectionsReleased> {

    Map<Long, Integer> fixedFeatureList;
    Participation participation;

    public Participation participation() {
        return participation;
    }

    public Map<Long, Integer> fixedFeatureList() {
        return fixedFeatureList;
    }

    public static EventSelectionsReleased create(Map<Long, Integer> eventFeatureList, Participation participation) {
        EventSelectionsReleased eventSelectionsFixed = new EventSelectionsReleased();
        eventSelectionsFixed.fixedFeatureList = eventFeatureList;
        eventSelectionsFixed.participation = participation;
        eventSelectionsFixed.fireEvent();
        return eventSelectionsFixed;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-SELECTION-RELEASED: " + fixedFeatureList.keySet().stream().map(Object::toString).collect(Collectors.joining(", ")) + " - ParticipationID: " + participation.getId();
    }

    @Override
    public EventSelectionsReleased self() {
        return this;
    }
}
