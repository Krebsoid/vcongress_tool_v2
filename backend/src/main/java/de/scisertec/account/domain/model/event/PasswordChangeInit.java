package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.PasswordRecoveryToken;
import de.scisertec.account.domain.model.User;
import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;
import de.scisertec.core.domain.model.template.TemplateContent;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PasswordChangeInit extends TrackedDomainEvent<PasswordChangeInit> implements TemplateContent {

    @Transient
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    PasswordRecoveryToken token;

    String groupName;

    @Transient
    @Inject
    ConfigurationManager configurationManager;

    public static PasswordChangeInit create(User user, String group) {
        PasswordChangeInit changePassword = new PasswordChangeInit().construct();
        changePassword.user(user)
                .token(PasswordRecoveryToken.generate());
        changePassword.groupName = group;
        changePassword.fireEvent();
        return changePassword;
    }

    public User user() {
        return user;
    }

    public PasswordRecoveryToken token() {
        return token;
    }

    public PasswordChangeInit user(User user) {
        this.user = user;
        return this;
    }

    public PasswordChangeInit token(PasswordRecoveryToken token) {
        this.token = token;
        return this;
    }

    public Map<String, Object> getContent() {
        HashMap<String, Object> mailContent = new HashMap<>();
        mailContent.put("token", token.value());
        mailContent.put("mail", user().credential().username());
        String host = configurationManager.getProperty("mail.linkhost");
        if(!groupName.equals("system")) {
            host = host + "/" + groupName;
        }
        mailContent.put("host", host);
        return mailContent;
    }

    @Override
    public PasswordChangeInit self() {
        return this;
    }

    @Override
    public String loggerStamp() {
        return "PASSWORD_CHANGE_INIT(" + groupName + "): User: " + user.credential().username() + " initiated password change with token: " + token.value();
    }
}
