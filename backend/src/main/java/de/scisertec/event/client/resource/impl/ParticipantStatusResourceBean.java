
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.service.ParticipantStatusService;
import de.scisertec.event.client.model.request.EventFlagCreationRequest;
import de.scisertec.event.client.model.request.ParticipantStatusCreationRequest;
import de.scisertec.event.client.resource.api.ParticipantStatusResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@ApplicationScoped
public class ParticipantStatusResourceBean implements ParticipantStatusResource {

    @Inject
    ParticipantStatusService participantStatusService;

    @Logging(out = true)
    public EventItemRepresentation getStatus(Long eventFeatureId, Long participantStatusId) {
        return participantStatusService.getStatus(eventFeatureId, participantStatusId);
    }

    @Logging(out = true)
    public List<EventItemRepresentation> getAllStatus(Long eventFeatureId) {
        return participantStatusService.getAllStatus(eventFeatureId);
    }

    @Logging(out = true)
    public EventItemRepresentation addStatus(Long eventFeatureId, @Valid ParticipantStatusCreationRequest participantStatusCreationRequest) {
        return participantStatusService.addStatus(eventFeatureId, participantStatusCreationRequest);
    }

    @Logging(out = true)
    public EventItemRepresentation addFlag(Long eventFeatureId, Long participantStatusId, @Valid EventFlagCreationRequest flagCreationRequest) {
        return participantStatusService.addFlag(participantStatusId, flagCreationRequest);
    }

    @Logging(out = true)
    public EventItemRepresentation removeFlag(Long eventFeatureId, Long participantStatusId, Long flagId) {
        return participantStatusService.removeFlag(participantStatusId, flagId);
    }

    @Logging(out = true)
    public EventItemRepresentation updateStatus(Long eventFeatureId, Long participantStatusId, @Valid ParticipantStatusCreationRequest participantStatusUpdateRequest) {
        return participantStatusService.editStatus(eventFeatureId, participantStatusId, participantStatusUpdateRequest);
    }

    @Logging(out = true)
    public EventItemRepresentation removeStatus(Long eventFeatureId, Long participantStatusId) {
        return participantStatusService.removeStatus(eventFeatureId, participantStatusId);
    }
}
