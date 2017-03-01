package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.Role;
import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.DomainEvent;

public class AddRole extends DomainEvent<AddRole> {

    String email;
    String role;

    User user;

    public static AddRole create(User user, Role role) {
        AddRole addRole = new AddRole();
        addRole.role = role.name();
        addRole.email = user.name();
        addRole.user = user;
        addRole.fireEvent();
        return addRole;
    }

    public User user() {
        return user;
    }

    @Override
    public String loggerStamp() {
        return "ADD-ROLE: " + email + " -> " + role;
    }

    @Override
    public AddRole self() {
        return this;
    }
}
