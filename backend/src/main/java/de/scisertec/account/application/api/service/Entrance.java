package de.scisertec.account.application.api.service;

import de.scisertec.account.application.api.model.command.LoginCommand;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.core.application.api.model.service.Service;

public interface Entrance extends Service {

    UserStateRepresentation login(LoginCommand login);
    UserStateRepresentation autoLogin(String autoLoginToken);
    UserStateRepresentation remoteLogin(String autoLoginToken);
    String getRemoteLoginLink();
    void logout();

    UserStateRepresentation activeState();

}
