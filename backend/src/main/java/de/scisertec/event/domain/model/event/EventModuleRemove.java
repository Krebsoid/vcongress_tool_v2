package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventModuleRemove extends DomainEvent<EventModuleRemove> {

    String module;
    Event event;

    public Event event() {
        return event;
    }

    public static EventModuleRemove create(Event event, String module) {
        EventModuleRemove eventCreation = new EventModuleRemove();
        eventCreation.event = event;
        eventCreation.module = module;
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-MODULE-REMOVE: " + event.identifier() + " - " + module;
    }

    @Override
    public EventModuleRemove self() {
        return this;
    }

}
