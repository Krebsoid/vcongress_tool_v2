package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.ParticipationRepresentation;
import de.scisertec.event.application.api.service.ParticipationService;
import de.scisertec.event.client.model.request.DinnerSaveRequest;
import de.scisertec.event.client.model.request.ParticipantNoteUpdateRequest;
import de.scisertec.event.client.model.request.ParticipationStatusSaveRequest;
import de.scisertec.event.client.resource.api.ParticipationResource;

import javax.inject.Inject;
import javax.validation.Valid;

public class ParticipationResourceBean implements ParticipationResource {

    @Inject
    ParticipationService participationService;

    @Logging
    public ParticipationRepresentation addStatus(@Valid ParticipationStatusSaveRequest participationSaveRequest) {
        return participationService.addStatus(participationSaveRequest);
    }

    @Logging
    public ParticipationRepresentation addDinner(@Valid DinnerSaveRequest dinnerSaveRequest) {
        return participationService.addDinner(dinnerSaveRequest);
    }

    @Logging
    public ParticipationRepresentation get() {
        return participationService.getParticipation();
    }

    @Logging
    public ParticipationRepresentation addStatus(Long id, @Valid ParticipationStatusSaveRequest participationSaveRequest) {
        return participationService.addStatus(id, participationSaveRequest);
    }

    @Logging
    public ParticipationRepresentation addDinner(Long id, @Valid DinnerSaveRequest dinnerSaveRequest) {
        return participationService.addDinner(id, dinnerSaveRequest);
    }

    @Logging
    public ParticipationRepresentation enable(Long id) {
        return participationService.enableParticipation(id);
    }

    @Logging
    public ParticipationRepresentation cancel(Long id) {
        return participationService.cancelParticipation(id);
    }

    @Logging
    public ParticipationRepresentation updateNote(Long id, @Valid ParticipantNoteUpdateRequest participantNoteUpdateRequest) {
        return participationService.updateNote(id, participantNoteUpdateRequest);
    }

    @Override
    public ParticipationRepresentation save(Long id, String method) {
        return participationService.save(id, method);
    }

    @Logging
    public ParticipationRepresentation get(Long id) {
        return participationService.getParticipation(id);
    }

    @Logging
    public void delete(Long id) {
        participationService.deleteParticipation(id);
    }
}
