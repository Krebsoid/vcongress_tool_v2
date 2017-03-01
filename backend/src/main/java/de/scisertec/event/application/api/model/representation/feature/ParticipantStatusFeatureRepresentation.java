package de.scisertec.event.application.api.model.representation.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;

import java.util.List;

public interface ParticipantStatusFeatureRepresentation extends EventFeatureRepresentation {

    List<EventItemRepresentation> getStatus();

}
