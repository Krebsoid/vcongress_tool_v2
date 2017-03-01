package de.scisertec.person.domain.model;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.*;

@Entity
public class Person extends DomainModel<Person> {

    String title = "";

    String firstName = "";
    String lastName = "";

    @Enumerated
    Gender gender = Gender.NO_GENDER;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Address address = new Address();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    InvoiceAddress invoiceAddress = new InvoiceAddress();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Occupation occupation = new Occupation();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Contact contact = new Contact();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    NotificationOptions notificationOptions = new NotificationOptions();

    public String title() {
        return title;
    }
    public Person title(String title) {
        this.title = title;
        return this;
    }

    public Gender gender() {
        return gender == null ? Gender.NO_GENDER : gender;
    }
    public Person gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public String firstName() {
        return firstName;
    }
    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String lastName() {
        return lastName;
    }
    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User user() {
        return user;
    }
    public Person user(User user) {
        this.user = user;
        return this;
    }

    public Address address() {
        return address;
    }
    public Person address(Address address) {
        this.address = address;
        return this;
    }

    public InvoiceAddress invoiceAddress() {
        return invoiceAddress;
    }
    public Person invoiceAddress(InvoiceAddress invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
        return this;
    }

    public Occupation occupation() {
        return occupation;
    }
    public Person occupation(Occupation occupation) {
        this.occupation = occupation;
        return this;
    }

    public Contact contact() {
        return contact;
    }
    public Person contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public NotificationOptions notificationOptions() {
        return notificationOptions;
    }
    public Person notificationOptions(NotificationOptions notificationOptions) {
        this.notificationOptions = notificationOptions;
        return this;
    }

    @Override
    public Person self() {
        return this;
    }
}
