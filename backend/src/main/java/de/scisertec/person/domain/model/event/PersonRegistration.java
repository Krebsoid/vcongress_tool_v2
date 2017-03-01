package de.scisertec.person.domain.model.event;

import de.scisertec.core.domain.model.base.TrackedDomainEvent;
import de.scisertec.core.domain.model.template.TemplateContent;
import de.scisertec.person.domain.model.Person;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PersonRegistration extends TrackedDomainEvent<PersonRegistration> implements TemplateContent {

    @Transient
    public Person person;

    public static PersonRegistration create(Person person) {
        PersonRegistration registration = new PersonRegistration();
        registration.person = person;
        registration.fireEvent();
        return registration;
    }

    @Override
    public String loggerStamp() {
        return "REGISTRATION: " + person.firstName() + " " + person.lastName() + " | " + person.user().name();
    }

    @Override
    public PersonRegistration self() {
        return this;
    }

    @Override
    public Map<String, Object> getContent() {
        HashMap<String, Object> content = new HashMap<>();
        content.put("person", person);
        return content;
    }
}
