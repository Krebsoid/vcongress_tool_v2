package de.scisertec.person.application.impl.service;

import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.person.application.api.model.representation.PersonListRepresentation;
import de.scisertec.person.application.api.service.PersonList;
import de.scisertec.person.application.impl.model.representation.PersonToListRepresentation;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.infrastructure.repository.PersonRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class PersonListBean implements PersonList {

    @Inject
    PersonRepository personRepository;

    @Inject
    GroupRepository groupRepository;

    public List<PersonListRepresentation> getAllPersons() {
        return personRepository.findByGroup(groupRepository.findByIdentifier("system")).stream()
                .map(PersonToListRepresentation::new)
                .collect(Collectors.toList());

    }

    public PersonListRepresentation getPerson(Long personId) {
        return new PersonToListRepresentation(personRepository.findBy(personId));
    }

    @Transactional
    public PersonListRepresentation deletePerson(Long personId) {
        Person person = personRepository.findBy(personId);
        person.user().deleted(true).save();
        return new PersonToListRepresentation(person);
    }

    @Transactional
    public PersonListRepresentation enablePerson(Long personId) {
        Person person = personRepository.findBy(personId);
        person.user().enabled(true).save();
        return new PersonToListRepresentation(person);
    }
}
