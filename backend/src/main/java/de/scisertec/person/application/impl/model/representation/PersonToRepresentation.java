package de.scisertec.person.application.impl.model.representation;


import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.application.impl.model.representation.UserToStateRepresentation;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.person.application.api.model.representation.*;
import de.scisertec.person.domain.model.Gender;
import de.scisertec.person.domain.model.Person;

public class PersonToRepresentation extends AbstractRepresentation implements PersonRepresentation {

    Long id;
    String title;
    Gender gender;

    String firstName;
    String lastName;

    UserStateRepresentation user;

    AddressRepresentation address;
    InvoiceAddressRepresentation invoiceAddress;
    OccupationRepresentation occupation;
    ContactRepresentation contact;

    public PersonToRepresentation(Person person) {
        this.id = person.getId();
        this.firstName = person.firstName();
        this.lastName = person.lastName();
        this.title = person.title();
        this.gender = person.gender();
        this.contact = new ContactToRepresentation(person.contact());
        this.occupation = new OccupationToRepresentation(person.occupation());
        this.address = new AddressToRepresentation(person.address());
        this.invoiceAddress = new InvoiceAddressToRepresentation(person.invoiceAddress());
        this.user = person.user() != null ? new UserToStateRepresentation(person.user()) : null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Gender getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserStateRepresentation getUser() {
        return user;
    }

    @Override
    public AddressRepresentation getAddress() {
        return address;
    }

    @Override
    public InvoiceAddressRepresentation getInvoiceAddress() {
        return invoiceAddress;
    }

    @Override
    public ContactRepresentation getContact() {
        return contact;
    }

    @Override
    public OccupationRepresentation getOccupation() {
        return occupation;
    }
}
