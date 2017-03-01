package de.scisertec.event.application.api.model.representation.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;

public interface DeadlineFeatureRepresentation extends EventFeatureRepresentation {

    String getDeadline();
    Boolean isDeadlineExpired();

}
