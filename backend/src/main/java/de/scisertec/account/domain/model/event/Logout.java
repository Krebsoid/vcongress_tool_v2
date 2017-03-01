package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.servlet.http.HttpSession;

@Entity
public class Logout extends TrackedDomainEvent<Logout> {

    String username;

    @Transient
    @Inject
    HttpSession httpSession;

    public static Logout create(User user) {
        Logout logout = new Logout();
        logout.username = user.credential().username();
        logout.fireEvent();
        logout.removeUserFromSession();
        return logout;
    }

    public String username() {
        return username;
    }

    public void removeUserFromSession() {
        httpSession.removeAttribute("user");
    }

    @Override
    public Logout self() {
        return this;
    }

    @Override
    public String loggerStamp() {
        return "LOGOUT - User: " + username + " logged out successfully";
    }
}
