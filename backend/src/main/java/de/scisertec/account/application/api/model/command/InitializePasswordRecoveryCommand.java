package de.scisertec.account.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface InitializePasswordRecoveryCommand extends Command {

    String getMail();

    String getGroup();

}
