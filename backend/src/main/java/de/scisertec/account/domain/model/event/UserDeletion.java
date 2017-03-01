package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.DomainEvent;

public class UserDeletion extends DomainEvent<UserDeletion> {

    String email;
    User user;

    public User user() {
        return user;
    }

    public static UserDeletion create(User user) {
        UserDeletion deletion = new UserDeletion();
        deletion.email = user.name();
        deletion.user = user;
        deletion.fireEvent();
        return deletion;
    }

    @Override
    public String loggerStamp() {
        return "USER-DELETED: " + email;
    }

    @Override
    public UserDeletion self() {
        return this;
    }
}
