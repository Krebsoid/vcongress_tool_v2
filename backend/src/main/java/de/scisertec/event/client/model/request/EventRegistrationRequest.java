package de.scisertec.event.client.model.request;

import com.sun.istack.NotNull;
import de.scisertec.account.infrastructure.validation.UniqueEmail;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.core.infrastructure.validation.FieldMatch;
import de.scisertec.event.application.api.model.command.EventRegistrationCommand;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@UniqueEmail(group = "group", email = "email")
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordRetype", message = "{de.scisertec.constraints.FieldMatch.Password.message}"),
        @FieldMatch(first = "email", second = "emailRetype", message = "{de.scisertec.constraints.FieldMatch.Email.message}")
})
public class EventRegistrationRequest implements EventRegistrationCommand, Request {

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
    @NotEmpty @Length(min = 8, max = 24)
    String password;
    @NotEmpty @Length(min = 8, max = 24)
    String passwordRetype;

    @NotEmpty
    String street;
    @NotEmpty
    String zipCode;
    @NotEmpty
    String city;
    String state;
    @NotEmpty
    String country;

    @NotEmpty
    String invoiceFullName;
    String invoiceInstitute;
    @NotEmpty
    String invoiceStreet;
    @NotEmpty
    String invoiceZipCode;
    @NotEmpty
    String invoiceCity;
    String invoiceState;
    @NotEmpty
    String invoiceCountry;

    @NotEmpty
    String phone;
    String fax;

    @NotEmpty
    String institute;
    String department;
    String position;

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
    public String getPassword() {
        return password;
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
    public String getStreet() {
        return street;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getInvoiceFullName() {
        return invoiceFullName;
    }

    @Override
    public String getInvoiceInstitute() {
        return invoiceInstitute != null ? invoiceInstitute : "";
    }

    @Override
    public String getInvoiceStreet() {
        return invoiceStreet;
    }

    @Override
    public String getInvoiceZipCode() {
        return invoiceZipCode;
    }

    @Override
    public String getInvoiceCity() {
        return invoiceCity;
    }

    @Override
    public String getInvoiceState() {
        return invoiceState;
    }

    @Override
    public String getInvoiceCountry() {
        return invoiceCountry;
    }

    @Override
    public String getFax() {
        return fax != null ? fax : "";
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getInstitute() {
        return institute;
    }

    @Override
    public String getDepartment() {
        return department != null ? department : "";
    }

    @Override
    public String getPosition() {
        return position != null ? position : "";
    }

    @Override
    public Boolean getNotificationRequest() {
        return notificationRequest;
    }

    public String getEmailRetype() {
        return emailRetype != null ? emailRetype.toLowerCase().trim() : "";
    }

    public String getPasswordRetype() {
        return passwordRetype;
    }
}
