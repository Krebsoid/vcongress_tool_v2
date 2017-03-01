package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Participation;

import java.util.Map;
import java.util.stream.Collectors;

public class EventSelectionsFixed extends DomainEvent<EventSelectionsFixed> {

    Map<Long, Integer> fixedFeatureList;
    Participation participation;

    public Participation participation() {
        return participation;
    }

    public Map<Long, Integer> fixedFeatureList() {
        return fixedFeatureList;
    }

    public static EventSelectionsFixed create(Map<Long, Integer> eventFeatureList, Participation participation) {
        EventSelectionsFixed eventSelectionsFixed = new EventSelectionsFixed();
        eventSelectionsFixed.fixedFeatureList = eventFeatureList;
        eventSelectionsFixed.participation = participation;
        eventSelectionsFixed.fireEvent();
        return eventSelectionsFixed;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-SELECTION-FIXED: " + fixedFeatureList.keySet().stream().map(Object::toString).collect(Collectors.joining(", ")) + " - ParticipationID: " + participation.getId();
    }

    @Override
    public EventSelectionsFixed self() {
        return this;
    }
}
