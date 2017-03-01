
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.service.DinnerService;
import de.scisertec.event.client.model.request.DinnerUpdateRequest;
import de.scisertec.event.client.model.request.EventItemCreationRequest;
import de.scisertec.event.client.resource.api.DinnerResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class DinnerResourceBean implements DinnerResource {

    @Inject
    DinnerService dinnerService;

    @Logging(out = true)
    public EventItemRepresentation getEventItem(Long eventFeatureId) {
        return dinnerService.getItem(eventFeatureId);
    }

    @Logging(out = true)
    public EventItemRepresentation updateEventItem(Long eventFeatureId, @Valid EventItemCreationRequest eventItemCreationRequest) {
        return dinnerService.editItem(eventFeatureId, eventItemCreationRequest);
    }
}
