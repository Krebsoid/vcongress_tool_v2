package de.scisertec.event.application.impl.model.representation;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventLogoRepresentation;
import de.scisertec.event.domain.model.EventLogo;

public class EventLogoToRepresentation extends AbstractRepresentation implements EventLogoRepresentation {

    Long id;
    String linkSmall;
    String linkLarge;
    String website;

    public EventLogoToRepresentation(EventLogo eventLogo) {
        this.linkLarge = eventLogo.linkLarge();
        this.linkSmall = eventLogo.linkSmall();
        this.id = eventLogo.getId();
        this.website = "";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getLinkSmall() {
        return linkSmall;
    }

    @Override
    public String getLinkLarge() {
        return linkLarge;
    }

    @Override
    public String getWebsite() {
        return website;
    }
}
