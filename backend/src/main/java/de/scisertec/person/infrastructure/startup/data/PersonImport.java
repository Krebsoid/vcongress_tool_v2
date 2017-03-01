package de.scisertec.person.infrastructure.startup.data;

import de.scisertec.account.infrastructure.startup.data.UserImport;
import de.scisertec.core.application.api.data.DataImportUnit;
import de.scisertec.person.domain.factory.PersonFactory;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.infrastructure.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PersonImport extends DataImportUnit {

    @Inject
    UserImport userImport;

    @Inject
    PersonFactory personFactory;

    @Inject
    PersonRepository personRepository;

    Person user;
    Person admin;
    Person customer;

    @Override
    protected Class importUnitClass() {
        return this.getClass();
    }

    protected void initialize() {
        user = personFactory.create("User", "User", userImport.getUser());
        personRepository.save(user);

        admin = personFactory.create("Admin", "Admin", userImport.getAdmin());
        personRepository.save(admin);

        customer = personFactory.create("Customer", "Customer", userImport.getCustomer());
        personRepository.save(customer);
    }

    public Person getUser() {
        return user;
    }

    public Person getAdmin() {
        return admin;
    }

    public Person getCustomer() {
        return customer;
    }
}
