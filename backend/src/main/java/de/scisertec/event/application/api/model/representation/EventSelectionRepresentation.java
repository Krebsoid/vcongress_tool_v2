package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

public interface EventSelectionRepresentation extends Representation {

    EventFeatureRepresentation getEventFeature();
    String getValue();
    Boolean getFixed();

}
