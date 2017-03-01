package de.scisertec.account.client.model.request;

import de.scisertec.account.application.api.model.command.InitializePasswordRecoveryCommand;
import de.scisertec.account.infrastructure.validation.ExistingEmail;
import de.scisertec.core.client.model.request.Request;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

@ExistingEmail(group = "group", email = "mail")
public class InitializePasswordRecoveryRequest implements InitializePasswordRecoveryCommand, Request {

    @NotEmpty
    @Email
    String mail;

    String group;

    public String getMail() {
        return mail != null ? mail.trim().toLowerCase() : "";
    }

    @Override
    public String getGroup() {
        return group == null || Objects.equals(group, "") ? "system" : group;
    }
}
