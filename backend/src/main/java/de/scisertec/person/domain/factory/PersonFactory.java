package de.scisertec.person.domain.factory;

import de.scisertec.account.domain.factory.UserFactory;
import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.factory.Factory;
import de.scisertec.person.domain.model.*;
import de.scisertec.person.infrastructure.repository.CountryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PersonFactory implements Factory {

    @Inject
    UserFactory userFactory;

    @Inject
    CountryRepository countryRepository;

    public Person create(String firstName, String lastName, User user) {
        Person person = new Person();
        person.firstName(firstName).lastName(lastName).user(user);
        return person;
    }

    public Person create(String email, String password, String title, String firstName, String lastName,
                         Gender gender, String street, String zipCode, String city, String state, String country,
                         String invoiceFullName, String invoiceInstitute, String invoiceStreet, String invoiceZipCode, String invoiceCity,
                         String invoiceState, String invoiceCountry,
                         String phone, String fax, String institute, String department, String position,
                         Boolean notificationRequest) {
        User user = userFactory.createWithoutGroup(email, password);
        Person person = new Person();
        Address address = new Address();
        InvoiceAddress invoiceAddress = new InvoiceAddress();
        Occupation occupation = new Occupation();
        Contact contact = new Contact();
        NotificationOptions notificationOptions = new NotificationOptions();
        address.street(street).zipCode(zipCode).city(city).state(state).country(countryRepository.findByISO(country));
        invoiceAddress.fullName(invoiceFullName).institute(invoiceInstitute).street(invoiceStreet)
                .zipCode(invoiceZipCode).city(invoiceCity).state(invoiceState).country(countryRepository.findByISO(invoiceCountry));
        occupation.department(department).institute(institute).position(position);
        contact.fax(fax).phone(phone);
        notificationOptions.notificationRequest(notificationRequest);
        person.firstName(firstName).lastName(lastName).gender(gender).title(title).notificationOptions(notificationOptions)
                .user(user).address(address).invoiceAddress(invoiceAddress).occupation(occupation).contact(contact);
        return person;
    }

    public Person create(String email, String title, String firstName, String lastName,
                         Gender gender, String city, String institute,
                         Boolean notificationRequest) {
        User user = userFactory.createWithoutGroup(email);
        Person person = new Person();
        Address address = new Address();
        InvoiceAddress invoiceAddress = new InvoiceAddress();
        Occupation occupation = new Occupation();
        Contact contact = new Contact();
        NotificationOptions notificationOptions = new NotificationOptions();
        address.city(city);
        occupation.institute(institute);
        notificationOptions.notificationRequest(notificationRequest);
        person.firstName(firstName).lastName(lastName).gender(gender).title(title).notificationOptions(notificationOptions)
                .user(user).address(address).invoiceAddress(invoiceAddress).occupation(occupation).contact(contact);
        return person;
    }

}
