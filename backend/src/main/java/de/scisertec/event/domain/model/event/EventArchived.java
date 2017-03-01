package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventArchived extends DomainEvent<EventArchived> {

    String identifier;
    Event event;

    public Event event() {
        return event;
    }

    public static EventArchived create(Event event) {
        EventArchived eventArchived = new EventArchived();
        eventArchived.event = event;
        eventArchived.identifier = event.identifier();
        eventArchived.fireEvent();
        return eventArchived;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-ARCHIVED: " + identifier;
    }

    @Override
    public EventArchived self() {
        return this;
    }

}
