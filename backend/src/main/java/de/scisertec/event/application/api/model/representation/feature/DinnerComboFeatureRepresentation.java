package de.scisertec.event.application.api.model.representation.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;

import java.util.List;

public interface DinnerComboFeatureRepresentation extends EventFeatureRepresentation {

    List<DinnerFeatureRepresentation> getDinnerFeatures();

}
