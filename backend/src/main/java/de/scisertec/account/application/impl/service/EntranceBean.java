package de.scisertec.account.application.impl.service;

import de.scisertec.account.application.api.model.command.LoginCommand;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.application.api.service.Entrance;
import de.scisertec.account.application.impl.model.representation.UserToStateRepresentation;
import de.scisertec.account.application.impl.service.authorization.LoginService;
import de.scisertec.account.application.impl.service.authorization.LogoutService;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.domain.model.event.AutoLogin;
import de.scisertec.account.domain.model.event.Login;
import de.scisertec.account.infrastructure.exception.LoginException;
import de.scisertec.account.infrastructure.exception.NotLoggedInException;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.infrastructure.qualifier.Active;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EntranceBean implements Entrance {

    @Inject
    LoginService loginService;

    @Inject
    LogoutService logoutService;

    @Inject
    UserRepository userRepository;

    @Inject
    @Active
    Instance<User> activeUser;

    public UserStateRepresentation login(LoginCommand loginCommand) {
        User user = userRepository.findByMailAddress(loginCommand.getUsername());
        if(user == null) {
            throw new LoginException();
        }
        loginService.login(user, loginCommand.getPassword(), loginCommand.getGroup());
        return new UserToStateRepresentation(user);
    }

    @Override
    public UserStateRepresentation autoLogin(String autoLoginToken) {
        User user = userRepository.findByAutoLoginToken(autoLoginToken);
        if(user == null) {
            throw new LoginException();
        }
        loginService.autoLogin(user);
        return new UserToStateRepresentation(user);
    }

    @Override
    public UserStateRepresentation remoteLogin(String remoteLoginToken) {
        User user = userRepository.findByRemoteLoginToken(remoteLoginToken);
        if(user == null) {
            throw new LoginException();
        }
        loginService.remoteLogin(user);
        return new UserToStateRepresentation(user);
    }

    @Inject
    ConfigurationManager configurationManager;

    @Override
    public String getRemoteLoginLink() {
        User user = activeUser.get();
        if(user == null) {
            throw new LoginException();
        }
        return configurationManager.getProperty("mail.linkhost") + "/remote-login?token=" + user.remoteEditingToken();
    }

    public void logout() {
        User user = assertLoggedIn();
        logoutService.logout(user);
    }

    public UserStateRepresentation activeState() {
        User user = assertLoggedIn();
        return new UserToStateRepresentation(user);
    }


    private User assertLoggedIn() {
        User user = activeUser.get();
        if(!user.isUser()) {
            throw new NotLoggedInException();
        }
        return user;
    }

}
