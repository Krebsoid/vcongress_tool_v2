package de.scisertec.event.domain.model.event;


import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventLogo;

public class EventLogoRemoved extends DomainEvent<EventLogoRemoved> {

    EventLogo eventLogo;

    public static EventLogoRemoved create(EventLogo eventLogo) {
        EventLogoRemoved eventLogoRemoved = new EventLogoRemoved();
        eventLogoRemoved.eventLogo = eventLogo;
        eventLogoRemoved.fireEvent();
        return eventLogoRemoved;
    }

    @Override
    public String loggerStamp() {
        return "REMOVED-EVENT-LOGO: " + eventLogo.getId();
    }

    @Override
    public EventLogoRemoved self() {
        return this;
    }
}
