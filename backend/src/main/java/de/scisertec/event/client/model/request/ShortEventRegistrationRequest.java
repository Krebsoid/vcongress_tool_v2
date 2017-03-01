package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import de.scisertec.account.infrastructure.validation.UniqueEmail;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.core.infrastructure.validation.FieldMatch;
import de.scisertec.event.application.api.model.command.ShortEventRegistrationCommand;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@UniqueEmail(group = "group", email = "email")
@FieldMatch.List({
        @FieldMatch(first = "email", second = "emailRetype", message = "{de.scisertec.constraints.FieldMatch.Email.message}")
})
public class ShortEventRegistrationRequest implements ShortEventRegistrationCommand, Request {

    @NotNull
    String group;

    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;

    @NotEmpty
    String gender;

    String title;

    @NotEmpty @Email
    String email;
    @NotEmpty
    String emailRetype;

    @NotEmpty
    String city;
    @NotEmpty
    String institute;

    Boolean notificationRequest;

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public String getEmail() {
        return email != null ? email.toLowerCase().trim() : "";
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getTitle() {
        return title != null ? title : "";
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getInstitute() {
        return institute;
    }

    public Boolean getNotificationRequest() {
        return notificationRequest;
    }

    public String getEmailRetype() {
        return emailRetype != null ? emailRetype.toLowerCase().trim() : "";
    }
}
