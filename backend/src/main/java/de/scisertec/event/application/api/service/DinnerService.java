package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.*;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;

import java.util.List;

public interface DinnerService extends Service {

    EventFeatureRepresentation createDinner(Long eventFeatureId, EventItemCreationCommand eventItemCreationCommand);
    EventFeatureRepresentation removeDinner(Long eventFeatureId, Long dinnerId);

    EventItemRepresentation getItem(Long eventFeatureId);
    EventItemRepresentation editItem(Long eventFeatureId, EventItemCreationCommand dinnerUpdateCommand);

}
