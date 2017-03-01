package de.scisertec.event.client.model.request;

import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.ParticipantNoteUpdateCommand;
import org.hibernate.validator.constraints.Length;

public class ParticipantNoteUpdateRequest implements ParticipantNoteUpdateCommand, Request {

    @Length(max = 1024)
    String note;

    @Override
    public String getNote() {
        return note;
    }

}
