package de.scisertec.person.infrastructure.service;

import de.scisertec.core.infrastructure.qualifier.TestEntity;
import de.scisertec.person.domain.factory.PersonFactory;
import de.scisertec.person.domain.model.Gender;
import de.scisertec.person.domain.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class TestPersonProducer {

    @Inject
    PersonFactory personFactory;

    @Produces
    @TestEntity
    public Person testPerson() {
        return personFactory.create("test@test.de", "Dr.", "Peter", "Mustermann", Gender.MALE, "Hannover", "MHH", Boolean.TRUE);
    }

}
