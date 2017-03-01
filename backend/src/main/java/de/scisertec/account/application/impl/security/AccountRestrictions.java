package de.scisertec.account.application.impl.security;


import de.scisertec.account.application.api.security.AdminRestricted;
import de.scisertec.account.application.api.security.LoggedIn;
import de.scisertec.account.application.api.security.LoggedOut;
import de.scisertec.account.application.api.security.UserRestricted;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.User;
import de.scisertec.core.infrastructure.qualifier.Active;
import org.apache.deltaspike.security.api.authorization.Secures;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class AccountRestrictions {

    @Inject
    @Active
    Instance<User> activeUser;

    @Inject
    GroupRepository groupRepository;

    @Secures
    @LoggedOut
    public Boolean isLoggedOut() {
        return activeUser.get() == null;
    }

    @Secures
    @LoggedIn
    public Boolean isLoggedIn() {
        return activeUser.get() != null;
    }

    @Secures
    @UserRestricted
    public Boolean isUser() {
        User user = activeUser.get();
        Group system = groupRepository.findByIdentifier("system");
        return user != null && user.hasRole(system, "USER");
    }

    @Secures
    @AdminRestricted
    public Boolean isAdmin() {
        User user = activeUser.get();
        Group system = groupRepository.findByIdentifier("system");
        return user != null && user.hasRole(system, "ADMIN");
    }

}
