package de.scisertec.person.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface PersonRegistrationCommand extends Command {

    String getFirstName();
    String getLastName();
    String getEmail();
    String getPassword();

    String getGroup();

}
