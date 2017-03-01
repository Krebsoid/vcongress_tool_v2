package de.scisertec.person.application.impl.service;

import de.scisertec.account.domain.factory.UserFactory;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.person.application.api.model.command.PersonRegistrationCommand;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.application.api.service.Registration;
import de.scisertec.person.application.impl.model.representation.PersonToRepresentation;
import de.scisertec.person.domain.factory.PersonFactory;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.domain.model.event.PersonRegistration;
import de.scisertec.person.infrastructure.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class RegistrationBean implements Registration {

    @Inject
    PersonFactory personFactory;

    @Inject
    UserFactory userFactory;

    @Inject
    GroupRepository groupRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    PersonRepository personRepository;

    @Transactional
    public PersonRepresentation register(PersonRegistrationCommand personRegistrationCommand) {
        User user = userFactory.create(personRegistrationCommand.getEmail(), personRegistrationCommand.getPassword()).enabled(false);
        userRepository.save(user);
        Person person = personFactory.create(personRegistrationCommand.getFirstName(),
                personRegistrationCommand.getLastName(), user);
        personRepository.save(person);
        PersonRegistration.create(person);
        return new PersonToRepresentation(person);
    }
}
