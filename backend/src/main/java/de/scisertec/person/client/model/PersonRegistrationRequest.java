package de.scisertec.person.client.model;

import de.scisertec.account.infrastructure.validation.UniqueEmail;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.core.infrastructure.validation.FieldMatch;
import de.scisertec.person.application.api.model.command.PersonRegistrationCommand;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordRetype", message = "{de.scisertec.constraints.FieldMatch.Password.message}"),
        @FieldMatch(first = "email", second = "emailRetype", message = "{de.scisertec.constraints.FieldMatch.Email.message}")
})
@UniqueEmail(group = "group", email = "email")
public class PersonRegistrationRequest implements PersonRegistrationCommand, Request {

    @NotEmpty
    String group = "system";

    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;

    @NotEmpty @Email
    String email;
    @NotEmpty
    String emailRetype;
    @NotEmpty @Length(min = 8, max = 24)
    String password;
    @NotEmpty @Length(min = 8, max = 24)
    String passwordRetype;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email != null ? email.toLowerCase().trim() : "";
    }

    public String getEmailRetype() {
        return emailRetype != null ? emailRetype.toLowerCase().trim() : "";
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRetype() {
        return passwordRetype;
    }

    public String getGroup() {
        return group;
    }
}
