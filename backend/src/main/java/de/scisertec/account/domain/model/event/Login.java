package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.exception.LoginException;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Login extends TrackedDomainEvent<Login> implements UserLogin {

    String username;
    String password;

    String groupName;

    Boolean success;

    @Transient
    User user;

    @Transient
    @Inject
    UserRepository userRepository;

    @Transient
    @Inject
    GroupRepository groupRepository;


    public static Login create(User user, String password, String group) {
        Login login = new Login().construct();
        user.remoteLoggedIn(Boolean.FALSE);
        login.username = user.name();
        login.password = password;
        login.groupName = group;
        login.user = user;
        login.validate();
        login.fireEvent();
        if(!login.success) {
            /*if(login.user() != null && !login.user().enabled()) {
                throw new AccountDisabledException();
            }*/
            throw new LoginException();
        }
        return login;
    }

    public Login validate() {
        Group group = groupRepository.findByIdentifier(this.groupName);
        User user = userRepository.findByGroupAndLogin(group, this);
        this.success = user != null && !user.deleted() && /*user.enabled() &&*/ user.credentialCheck(this);
        return this;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String group() {
        return groupName;
    }

    public User user() {
        return user;
    }

    public Boolean success() {
        return success;
    }

    @Override
    public Login self() {
        return this;
    }

    @Override
    public String loggerStamp() {
        String successString = success ? "SUCCESS" :  "FAILED";
        String enabledString;
        if(user != null) {
            enabledString = user().enabled() ? "ENABLED" :  "DISABLED";
        } else {
            enabledString = "UNKNOWN_USER";
        }
        return "LOGIN("+ this.groupName +") - User: " + username + " tried to login with password: " + password + " -> " + successString + " " + enabledString;
    }
}
