package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventTestModeOff extends DomainEvent<EventTestModeOff> {

    String identifier;
    Event event;

    public Event event() {
        return event;
    }

    public static EventTestModeOff create(Event event) {
        EventTestModeOff eventCreation = new EventTestModeOff();
        eventCreation.event = event;
        eventCreation.identifier = event.identifier();
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-TESTMODE-OFF: " + identifier;
    }

    @Override
    public EventTestModeOff self() {
        return this;
    }

}
