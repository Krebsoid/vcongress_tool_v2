package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventModuleAdd extends DomainEvent<EventModuleAdd> {

    String module;
    Event event;

    public Event event() {
        return event;
    }

    public static EventModuleAdd create(Event event, String module) {
        EventModuleAdd eventCreation = new EventModuleAdd();
        eventCreation.event = event;
        eventCreation.module = module;
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-MODULE-ADD: " + event.identifier() + " - " + module;
    }

    @Override
    public EventModuleAdd self() {
        return this;
    }

}
