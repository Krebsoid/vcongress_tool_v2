package de.scisertec.person.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface ContactEditCommand extends Command {

    String getPhone();
    String getFax();

}
