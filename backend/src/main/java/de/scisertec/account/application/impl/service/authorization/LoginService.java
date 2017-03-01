package de.scisertec.account.application.impl.service.authorization;

import de.scisertec.account.domain.model.User;
import de.scisertec.account.domain.model.event.AutoLogin;
import de.scisertec.account.domain.model.event.Login;
import de.scisertec.account.domain.model.event.RemoteLogin;

public class LoginService {

    public Login login(User user, String password, String group) {
        return Login.create(user, password, group);
    }

    public RemoteLogin remoteLogin(User user) {
        return RemoteLogin.create(user);
    }

    public AutoLogin autoLogin(User user) {
        return AutoLogin.create(user);
    }

}
