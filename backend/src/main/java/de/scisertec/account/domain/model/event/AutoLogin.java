package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class AutoLogin extends TrackedDomainEvent<AutoLogin> implements UserLogin {

    String username;

    @Transient
    User user;

    @Transient
    @Inject
    UserRepository userRepository;

    @Transient
    @Inject
    GroupRepository groupRepository;

    public static AutoLogin create(User user) {
        AutoLogin login = new AutoLogin().construct();
        user.remoteLoggedIn(Boolean.TRUE);
        login.username = user.name();
        login.user = user;
        login.fireEvent();
        return login;
    }


    public String username() {
        return username;
    }


    public Boolean success() {
        return Boolean.TRUE;
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public String group() {
        return "system";
    }

    @Override
    public AutoLogin self() {
        return this;
    }

    @Override
    public String loggerStamp() {
        return "AUTO-LOGIN - User: " + username + " was auto-logged-in";
    }
}
