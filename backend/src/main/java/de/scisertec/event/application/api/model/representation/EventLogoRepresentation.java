package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.event.domain.model.Participation;

import java.math.BigDecimal;
import java.util.List;

public interface EventLogoRepresentation extends Representation {

    Long getId();

    String getLinkSmall();
    String getLinkLarge();

    String getWebsite();

}
