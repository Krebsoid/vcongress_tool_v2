package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventPublish extends DomainEvent<EventPublish> {

    String identifier;
    Boolean published;
    Event event;

    public Event event() {
        return event;
    }

    public static EventPublish create(Event event) {
        EventPublish eventPublish = new EventPublish();
        eventPublish.event = event;
        eventPublish.identifier = event.identifier();
        eventPublish.published = event.published();
        eventPublish.fireEvent();
        return eventPublish;
    }

    @Override
    public String loggerStamp() {
        String eventName = event.published() ? "EVENT-PUBLISH" : "EVENT-REVOKE";
        return eventName + ": " + identifier;
    }

    @Override
    public EventPublish self() {
        return this;
    }

}
