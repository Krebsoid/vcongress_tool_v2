package de.scisertec.account.client.model.request;

import de.scisertec.account.application.api.model.command.ExecutePasswordRecoveryCommand;
import de.scisertec.account.infrastructure.validation.ExistingEmail;
import de.scisertec.account.infrastructure.validation.ValidPasswordChangeToken;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.core.infrastructure.validation.FieldMatch;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@FieldMatch.List({
        @FieldMatch(first = "newPassword", second = "newPasswordRetype", message = "{de.scisertec.constraints.FieldMatch.Password.message}")
})
@ExistingEmail(group = "group", email = "mail")
public class ExecutePasswordRecoveryRequest implements ExecutePasswordRecoveryCommand, Request {

    @NotNull
    @Email
    String mail;

    @NotNull
    @ValidPasswordChangeToken
    String token;

    @NotNull
    String newPassword;
    @NotNull
    String newPasswordRetype;

    String group;

    public String getMail() {
        return mail != null ? mail.trim().toLowerCase() : "";
    }

    public String getToken() {
        return token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordRetype() {
        return newPasswordRetype;
    }

    @Override
    public String getGroup() {
        return group == null || Objects.equals(group, "") ? "system" : group;
    }
}
