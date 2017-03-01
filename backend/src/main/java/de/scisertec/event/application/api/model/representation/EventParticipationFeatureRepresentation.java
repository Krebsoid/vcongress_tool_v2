package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

public interface EventParticipationFeatureRepresentation extends Representation, EventFeatureRepresentation {

    String getLabel();
    String getDescription();
    Boolean getRequired();

}
