package de.scisertec.person.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.account.infrastructure.validation.UniqueEmail;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.person.application.api.model.command.InvoiceAddressEditCommand;
import de.scisertec.person.application.api.model.command.PersonEditCommand;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
@UniqueEmail(omitSelf = true, email = "email")
public class PersonAdminEditRequest implements PersonEditCommand, Request {

    String gender;

    String title;

    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;

    @NotEmpty @Email
    String email;

    @Valid
    AddressAdminEditRequest address;

    @Valid
    OccupationAdminEditRequest occupation;

    @Valid
    ContactAdminEditRequest contact;

    public String getTitle() {
        return title;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email != null ? email.toLowerCase().trim() : "";
    }

    public AddressAdminEditRequest getAddress() {
        return address;
    }

    @Override
    public InvoiceAddressEditCommand getInvoiceAddress() {
        return null;
    }

    public OccupationAdminEditRequest getOccupation() {
        return occupation;
    }

    public ContactAdminEditRequest getContact() {
        return contact;
    }

}
