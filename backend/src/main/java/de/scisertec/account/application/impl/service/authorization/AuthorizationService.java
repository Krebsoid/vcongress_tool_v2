package de.scisertec.account.application.impl.service.authorization;

import de.scisertec.account.domain.model.Nobody;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.domain.model.event.Login;
import de.scisertec.account.domain.model.event.Logout;
import de.scisertec.account.domain.model.event.UserLogin;
import de.scisertec.core.infrastructure.qualifier.Active;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import java.io.Serializable;

@SessionScoped
public class AuthorizationService implements Serializable {

    User activeUser = new Nobody();

    String activeGroup = "";

    @Produces
    @Active
    public User getActiveUser() {
        return activeUser;
    }

    @Produces
    @Active
    public String getActiveGroup() {
        return activeGroup;
    }

    public void setActiveUser(User user) {
        activeUser = user;
    }

    public void observeLogin(@Observes UserLogin login) {
        if(login.success()) {
            activeUser = login.user();
            activeGroup = login.group();
        }
    }

    public void observeLogout(@Observes Logout logout) {
        activeUser = new Nobody();
        activeGroup = "";
    }


}
