package de.scisertec.account.application.api.service;

import de.scisertec.account.application.api.model.command.ExecutePasswordRecoveryCommand;
import de.scisertec.account.application.api.model.command.InitializePasswordRecoveryCommand;
import de.scisertec.core.application.api.model.service.Service;

public interface PasswordRecovery extends Service {

    void changePassword(ExecutePasswordRecoveryCommand command);
    void initiatePasswordChange(InitializePasswordRecoveryCommand command);

}
