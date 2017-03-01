package de.scisertec.account.infrastructure.exception;

import de.scisertec.account.domain.model.User;

public class AlreadyLoggedInException extends RuntimeException {

    String username;

    public AlreadyLoggedInException(User user) {
        this.username = user.credential().username();
    }

    public String getUsername() {
        return username;
    }
}
