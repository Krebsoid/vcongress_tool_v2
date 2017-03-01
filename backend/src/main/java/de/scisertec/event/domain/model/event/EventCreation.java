package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;
import de.scisertec.event.domain.model.Event;

import javax.persistence.Entity;
import javax.persistence.Transient;

public class EventCreation extends DomainEvent<EventCreation> {

    String identifier;
    Event event;

    public Event event() {
        return event;
    }

    public static EventCreation create(Event event) {
        EventCreation eventCreation = new EventCreation();
        eventCreation.event = event;
        eventCreation.identifier = event.identifier();
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-CREATION: " + identifier;
    }

    @Override
    public EventCreation self() {
        return this;
    }

}
