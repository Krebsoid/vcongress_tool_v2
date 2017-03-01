package de.scisertec.person.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface OccupationEditCommand extends Command {

    String getInstitute();
    String getDepartment();
    String getPosition();

}
