
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.service.DinnerService;
import de.scisertec.event.client.model.request.EventItemCreationRequest;
import de.scisertec.event.client.resource.api.DinnerComboResource;
import de.scisertec.event.client.resource.api.DinnerResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class DinnerComboResourceBean implements DinnerComboResource {

    @Inject
    DinnerService dinnerService;

    @Logging
    public EventFeatureRepresentation createDinner(Long eventFeatureId, @Valid EventItemCreationRequest dinnerUpdateRequest) {
        return dinnerService.createDinner(eventFeatureId, dinnerUpdateRequest);
    }

    @Logging
    public EventFeatureRepresentation removeDinner(Long eventFeatureId, Long dinnerId) {
        return dinnerService.removeDinner(eventFeatureId, dinnerId);
    }
}
