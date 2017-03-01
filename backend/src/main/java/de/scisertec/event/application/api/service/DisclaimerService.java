package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.feature.DisclaimerUpdateCommand;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;

public interface DisclaimerService extends Service {

    EventFeatureRepresentation updateDisclaimerEventFeature(Long eventFeatureId, DisclaimerUpdateCommand disclaimerUpdateCommand);

}
