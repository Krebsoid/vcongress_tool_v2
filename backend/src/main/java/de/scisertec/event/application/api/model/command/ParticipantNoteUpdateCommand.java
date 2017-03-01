package de.scisertec.event.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface ParticipantNoteUpdateCommand extends Command {

    String getNote();

}
