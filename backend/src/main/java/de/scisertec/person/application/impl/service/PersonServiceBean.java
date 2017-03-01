package de.scisertec.person.application.impl.service;

import de.scisertec.account.application.impl.service.authorization.AuthorizationService;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.exception.NotLoggedInException;
import de.scisertec.core.infrastructure.qualifier.Active;
import de.scisertec.person.application.api.model.command.InvoiceAddressEditCommand;
import de.scisertec.person.application.api.model.command.PersonEditCommand;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.application.api.service.PersonService;
import de.scisertec.person.application.impl.model.representation.PersonToRepresentation;
import de.scisertec.person.domain.model.Gender;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.domain.model.event.PersonEdit;
import de.scisertec.person.infrastructure.repository.CountryRepository;
import de.scisertec.person.infrastructure.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class PersonServiceBean implements PersonService {

    @Inject
    @Active
    Instance<User> user;

    @Inject
    PersonRepository personRepository;

    @Inject
    CountryRepository countryRepository;

    @Inject
    AuthorizationService authorizationService;

    @Override
    public PersonRepresentation getPerson(Long id) {
        Person person = personRepository.findBy(id);
        return new PersonToRepresentation(person);
    }

    @Override
    public PersonRepresentation editPerson(PersonEditCommand personEditCommand) {
        Person person = personRepository.findByUser(user.get());
        person.firstName(personEditCommand.getFirstName()).lastName(personEditCommand.getLastName())
                .gender(Gender.valueOf(personEditCommand.getGender())).title(personEditCommand.getTitle()).save();
        person.user().credential().username(personEditCommand.getEmail()).save();
        person.address().city(personEditCommand.getAddress().getCity()).street(personEditCommand.getAddress().getStreet())
                .zipCode(personEditCommand.getAddress().getZipCode())
                .state(personEditCommand.getAddress().getState())
                .country(countryRepository.findByISO(personEditCommand.getAddress().getCountry().getIsoCode()));
        person.invoiceAddress().fullName(personEditCommand.getInvoiceAddress().getFullName()).institute(personEditCommand.getInvoiceAddress().getInstitute())
                .city(personEditCommand.getInvoiceAddress().getCity()).street(personEditCommand.getInvoiceAddress().getStreet())
                .zipCode(personEditCommand.getInvoiceAddress().getZipCode())
                .state(personEditCommand.getInvoiceAddress().getState())
                .country(countryRepository.findByISO(personEditCommand.getInvoiceAddress().getCountry().getIsoCode()));
        person.contact().fax(personEditCommand.getContact().getFax()).phone(personEditCommand.getContact().getPhone());
        person.occupation().institute(personEditCommand.getOccupation().getInstitute()).department(personEditCommand.getOccupation().getDepartment())
                .position(personEditCommand.getOccupation().getPosition());
        authorizationService.setActiveUser(person.user());
        PersonEdit.create(person);
        return new PersonToRepresentation(person);
    }

    @Override
    public PersonRepresentation editPersonInvoiceAddress(InvoiceAddressEditCommand invoiceAddressEditCommand) {
        Person person = personRepository.findByUser(user.get());
        person.invoiceAddress().fullName(invoiceAddressEditCommand.getFullName()).institute(invoiceAddressEditCommand.getInstitute())
                .city(invoiceAddressEditCommand.getCity()).street(invoiceAddressEditCommand.getStreet())
                .zipCode(invoiceAddressEditCommand.getZipCode())
                .state(invoiceAddressEditCommand.getState())
                .country(countryRepository.findByISO(invoiceAddressEditCommand.getCountry().getIsoCode()));
        PersonEdit.create(person);
        return new PersonToRepresentation(person);
    }

    @Override
    public PersonRepresentation editPerson(Long id, PersonEditCommand personEditCommand) {
        Person person = personRepository.findBy(id);
        person.firstName(personEditCommand.getFirstName()).lastName(personEditCommand.getLastName())
                .title(personEditCommand.getTitle()).save();
        if(personEditCommand.getGender() != null) {
            person.gender(Gender.valueOf(personEditCommand.getGender()));
        }
        person.user().credential().username(personEditCommand.getEmail()).save();
        person.address().city(personEditCommand.getAddress().getCity()).street(personEditCommand.getAddress().getStreet())
                .zipCode(personEditCommand.getAddress().getZipCode())
                .state(personEditCommand.getAddress().getState());
        if(personEditCommand.getAddress().getCountry() != null) {
            person.address().country(countryRepository.findByISO(personEditCommand.getAddress().getCountry().getIsoCode()));
        }
        if(personEditCommand.getInvoiceAddress() != null) {
            person.invoiceAddress().fullName(personEditCommand.getInvoiceAddress().getFullName()).institute(personEditCommand.getInvoiceAddress().getInstitute())
                    .city(personEditCommand.getInvoiceAddress().getCity()).street(personEditCommand.getInvoiceAddress().getStreet())
                    .zipCode(personEditCommand.getInvoiceAddress().getZipCode())
                    .state(personEditCommand.getInvoiceAddress().getState())
                    .country(countryRepository.findByISO(personEditCommand.getInvoiceAddress().getCountry().getIsoCode()));
        }
        person.contact().fax(personEditCommand.getContact().getFax()).phone(personEditCommand.getContact().getPhone());
        person.occupation().institute(personEditCommand.getOccupation().getInstitute()).department(personEditCommand.getOccupation().getDepartment())
                .position(personEditCommand.getOccupation().getPosition());
        PersonEdit.create(person);
        return new PersonToRepresentation(person);
    }

    @Override
    public PersonRepresentation activeState() {
        if(user.get().isUser()) {
            Person person = personRepository.findByUser(user.get());
            return new PersonToRepresentation(person);
        } else {
            throw new NotLoggedInException();
        }

    }
}
