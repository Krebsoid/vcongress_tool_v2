package de.scisertec.account.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface LoginCommand extends Command {

    String getUsername();
    String getPassword();

    String getGroup();

}
