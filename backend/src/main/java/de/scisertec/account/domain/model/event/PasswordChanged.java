package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.PasswordRecoveryToken;
import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;

import javax.persistence.*;

@Entity
public class PasswordChanged extends TrackedDomainEvent<PasswordChanged> {

    @Transient
    User user;

    String oldPassword;
    String newPassword;

    String groupName;

    @OneToOne
    PasswordRecoveryToken token;

    public static PasswordChanged create(User user, PasswordRecoveryToken token, String newPassword, String oldPassword, String group) {
        PasswordChanged passwordChanged = new PasswordChanged();
        passwordChanged.user = user;
        passwordChanged.token = token;
        passwordChanged.newPassword = newPassword;
        passwordChanged.oldPassword = oldPassword;
        passwordChanged.groupName = group;
        token.setUsed();
        passwordChanged.fireEvent();
        return passwordChanged;
    }

    public User user() {
        return user;
    }

    public PasswordRecoveryToken token() {
        return token;
    }

    public String newPassword() {
        return newPassword;
    }

    public PasswordChanged user(User user) {
        this.user = user;
        return this;
    }

    public PasswordChanged token(PasswordRecoveryToken token) {
        this.token = token;
        return this;
    }

    @Override
    public PasswordChanged self() {
        return this;
    }

    @Override
    public String loggerStamp() {
        return "PASSWORD_CHANGED("+ this.groupName +"): User: " + user.credential().username() + " changed password to " + newPassword + " with token: " + token.value();
    }
}
