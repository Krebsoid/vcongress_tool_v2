package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

public interface EventSelectionListRepresentation extends Representation {

    Long getEventFeatureId();
    String getEventFeature();
    String getValue();
    Boolean getFixed();

}
