package de.scisertec.account.application.impl.service.authorization;

import de.scisertec.account.domain.model.User;
import de.scisertec.account.domain.model.event.Logout;

public class LogoutService {

    public Logout logout(User user) {
        return Logout.create(user);
    }
}
