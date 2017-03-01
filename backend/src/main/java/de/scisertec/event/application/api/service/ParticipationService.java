package de.scisertec.event.application.api.service;

import de.scisertec.event.application.api.model.command.ParticipantNoteUpdateCommand;
import de.scisertec.event.application.api.model.command.eventitem.DinnerSaveCommand;
import de.scisertec.event.application.api.model.command.eventitem.ParticipationStatusSaveCommand;
import de.scisertec.event.application.api.model.representation.ParticipationRepresentation;

public interface ParticipationService {

    ParticipationRepresentation getParticipation();
    ParticipationRepresentation addStatus(ParticipationStatusSaveCommand participationStatusSaveCommand);
    ParticipationRepresentation addDinner(DinnerSaveCommand dinnerSaveCommand);


    ParticipationRepresentation getParticipation(Long id);
    void deleteParticipation(Long id);
    ParticipationRepresentation addStatus(Long id, ParticipationStatusSaveCommand participationStatusSaveCommand);
    ParticipationRepresentation addDinner(Long id, DinnerSaveCommand dinnerSaveCommand);

    ParticipationRepresentation cancelParticipation(Long id);
    ParticipationRepresentation enableParticipation(Long id);

    ParticipationRepresentation save(Long id, String method);

    ParticipationRepresentation updateNote(Long id, ParticipantNoteUpdateCommand participantNoteUpdateCommand);

}
