
package de.scisertec.person.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.person.application.api.model.representation.PersonListRepresentation;
import de.scisertec.person.application.api.service.PersonList;
import de.scisertec.person.client.resource.api.PersonResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PersonResourceBean implements PersonResource {

    @Inject
    PersonList personList;

    @Logging
    public List<PersonListRepresentation> getAllPersons() {
        return personList.getAllPersons();
    }

    @Logging(out = true)
    public PersonListRepresentation getPerson(Long personId) {
        return personList.getPerson(personId);
    }

    @Logging(out = true)
    public PersonListRepresentation enablePerson(Long personId) {
        return personList.enablePerson(personId);
    }

    @Logging(out = true)
    public PersonListRepresentation deletePerson(Long personId) {
        return personList.deletePerson(personId);
    }
}
