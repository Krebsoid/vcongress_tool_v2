package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;

public class EventLicenseSet extends DomainEvent<EventLicenseSet> {

    String license;
    Event event;

    public Event event() {
        return event;
    }

    public static EventLicenseSet create(Event event, String license) {
        EventLicenseSet eventCreation = new EventLicenseSet();
        eventCreation.event = event;
        eventCreation.license = license;
        eventCreation.fireEvent();
        return eventCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-LICENSE-SET: " + event.identifier() + " - " + license;
    }

    @Override
    public EventLicenseSet self() {
        return this;
    }

}
