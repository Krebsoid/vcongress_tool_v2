
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.service.EventFeatureService;
import de.scisertec.event.client.model.request.EventParticipationFeatureUpdateRequest;
import de.scisertec.event.client.resource.api.EventFeatureResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class EventFeatureResourceBean implements EventFeatureResource {

    @Inject
    EventFeatureService eventFeatureService;

    @Logging
    public EventFeatureRepresentation updateEventFeature(Long eventFeatureId, @Valid EventParticipationFeatureUpdateRequest eventFeatureUpdateRequest) {
        return eventFeatureService.updateEventFeature(eventFeatureId, eventFeatureUpdateRequest);
    }

    @Logging
    public EventFeatureRepresentation updateParticipationEventFeature(Long eventFeatureId, @Valid EventParticipationFeatureUpdateRequest eventFeatureUpdateRequest) {
        return eventFeatureService.updateParticipationEventFeature(eventFeatureId, eventFeatureUpdateRequest);
    }


    @Logging
    public EventFeatureRepresentation removeEventFeature(Long eventFeatureId) {
        return eventFeatureService.removeEventFeature(eventFeatureId);
    }
}
