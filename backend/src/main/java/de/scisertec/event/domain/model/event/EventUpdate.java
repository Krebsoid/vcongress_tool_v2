package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventUpdate extends DomainEvent<EventUpdate> {

    String identifier;
    Event event;

    public Event event() {
        return event;
    }

    public static EventUpdate create(Event event) {
        EventUpdate eventCreation = new EventUpdate();
        eventCreation.event = event;
        eventCreation.identifier = event.identifier();
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-UPDATE: " + identifier;
    }

    @Override
    public EventUpdate self() {
        return this;
    }

}
