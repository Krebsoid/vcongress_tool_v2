package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.DomainEvent;

public class UserEnable extends DomainEvent<UserEnable> {

    String email;
    Boolean enable;
    User user;

    public User user() {
        return user;
    }

    public static UserEnable create(User user) {
        UserEnable enable = new UserEnable();
        enable.email = user.name();
        enable.user = user;
        enable.enable = user.enabled();
        enable.fireEvent();
        return enable;
    }

    @Override
    public String loggerStamp() {
        if(user.enabled()) {
            return "USER-ENABLED: " + email;
        } else {
            return "USER-DISABLED: " + email;
        }
    }

    @Override
    public UserEnable self() {
        return this;
    }
}
