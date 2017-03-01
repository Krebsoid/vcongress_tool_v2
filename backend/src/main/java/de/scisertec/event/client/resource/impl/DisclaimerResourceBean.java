
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.service.DisclaimerService;
import de.scisertec.event.client.model.request.DisclaimerUpdateRequest;
import de.scisertec.event.client.resource.api.DisclaimerResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class DisclaimerResourceBean implements DisclaimerResource {

    @Inject
    DisclaimerService disclaimerService;

    @Logging
    public EventFeatureRepresentation updateDisclaimer(Long eventFeatureId, @Valid DisclaimerUpdateRequest disclaimerUpdateRequest) {
        return disclaimerService.updateDisclaimerEventFeature(eventFeatureId, disclaimerUpdateRequest);
    }
}
