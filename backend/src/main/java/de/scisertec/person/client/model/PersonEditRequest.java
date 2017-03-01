package de.scisertec.person.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.account.infrastructure.validation.UniqueEmail;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.person.application.api.model.command.InvoiceAddressEditCommand;
import de.scisertec.person.application.api.model.command.PersonEditCommand;
import de.scisertec.person.domain.model.InvoiceAddress;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@UniqueEmail(omitSelf = true, email = "email")
public class PersonEditRequest implements PersonEditCommand, Request {

    @NotEmpty
    String gender;

    String title;

    @NotEmpty
    String firstName;
    @NotEmpty
    String lastName;

    @NotEmpty @Email
    String email;

    @NotNull @Valid
    AddressEditRequest address;

    @NotNull @Valid
    InvoiceAddressEditRequest invoiceAddress;

    @NotNull @Valid
    OccupationEditRequest occupation;

    @NotNull @Valid
    ContactEditRequest contact;

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

    public AddressEditRequest getAddress() {
        return address;
    }

    public InvoiceAddressEditRequest getInvoiceAddress() {
        return invoiceAddress;
    }

    public OccupationEditRequest getOccupation() {
        return occupation;
    }

    public ContactEditRequest getContact() {
        return contact;
    }

}
