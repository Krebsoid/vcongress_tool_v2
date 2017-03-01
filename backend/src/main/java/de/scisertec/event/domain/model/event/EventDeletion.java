package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventDeletion extends DomainEvent<EventDeletion> {

    String identifier;
    Event event;

    public Event event() {
        return event;
    }

    public static EventDeletion create(Event event) {
        EventDeletion eventCreation = new EventDeletion();
        eventCreation.event = event;
        eventCreation.identifier = event.identifier();
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-DELETION: " + identifier;
    }

    @Override
    public EventDeletion self() {
        return this;
    }

}
