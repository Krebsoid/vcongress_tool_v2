package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.Role;
import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.DomainEvent;

public class RemoveRole extends DomainEvent<RemoveRole> {

    String email;
    String role;
    User user;

    public static RemoveRole create(User user, Role role) {
        RemoveRole removeRole = new RemoveRole();
        removeRole.role = role.name();
        removeRole.email = user.name();
        removeRole.user = user;
        removeRole.fireEvent();
        return removeRole;
    }

    public User user() {
        return user;
    }

    @Override
    public String loggerStamp() {
        return "REMOVE-ROLE: " + email + " -> " + role;
    }

    @Override
    public RemoveRole self() {
        return this;
    }
}
