package de.scisertec.account.client.model.request;

import de.scisertec.account.application.api.model.command.LoginCommand;
import de.scisertec.core.client.model.request.Request;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class LoginRequest implements LoginCommand, Request {

    @NotNull
    String username;
    @NotNull
    String password;

    String group;

    public String getUsername() {
        return username != null ? username.trim().toLowerCase() : "";
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getGroup() {
        return group == null || Objects.equals(group, "") ? "system" : group;
    }
}
