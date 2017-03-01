package de.scisertec.account.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface ExecutePasswordRecoveryCommand extends Command {

    String getMail();
    String getToken();
    String getNewPassword();
    String getNewPasswordRetype();

    String getGroup();

}
