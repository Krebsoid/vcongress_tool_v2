package de.scisertec.event.domain.model.event;


import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventLogo;

public class EventLogoUploaded extends DomainEvent<EventLogoUploaded> {

    EventLogo eventLogo;

    public static EventLogoUploaded create(EventLogo eventLogo) {
        EventLogoUploaded eventLogoUploaded = new EventLogoUploaded();
        eventLogoUploaded.eventLogo = eventLogo;
        eventLogoUploaded.fireEvent();
        return eventLogoUploaded;
    }

    @Override
    public String loggerStamp() {
        return "UPLOAD-EVENT-LOGO: " + eventLogo.getId() + " - " + eventLogo.linkSmall();
    }

    @Override
    public EventLogoUploaded self() {
        return this;
    }
}
