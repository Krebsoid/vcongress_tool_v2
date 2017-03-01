package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventTestModeOn extends DomainEvent<EventTestModeOn> {

    String identifier;
    Event event;

    public Event event() {
        return event;
    }

    public static EventTestModeOn create(Event event) {
        EventTestModeOn eventCreation = new EventTestModeOn();
        eventCreation.event = event;
        eventCreation.identifier = event.identifier();
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-TESTMODE-ON: " + identifier;
    }

    @Override
    public EventTestModeOn self() {
        return this;
    }

}
