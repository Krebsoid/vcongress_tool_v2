package de.scisertec.person.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.person.domain.model.Person;

public class PersonEdit extends DomainEvent<PersonEdit> {

    String firstName;
    String lastName;
    String email;
    Person person;

    public Person person() {
        return person;
    }

    public static PersonEdit create(Person person) {
        PersonEdit edit = new PersonEdit();
        edit.firstName = person.firstName();
        edit.lastName = person.lastName();
        edit.email = person.user().credential().username();
        edit.person = person;
        edit.fireEvent();
        return edit;
    }

    @Override
    public String loggerStamp() {
        return "PERSON-EDIT: " + person.getId() + " | " + firstName + " | " + lastName + " | " + email;
    }

    @Override
    public PersonEdit self() {
        return this;
    }
}
