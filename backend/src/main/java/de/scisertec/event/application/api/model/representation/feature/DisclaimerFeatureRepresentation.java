package de.scisertec.event.application.api.model.representation.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;

public interface DisclaimerFeatureRepresentation extends EventFeatureRepresentation {

    String getContent();
    Integer getIndex();
    Boolean getMandatory();

}
