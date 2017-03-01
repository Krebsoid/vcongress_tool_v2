package de.scisertec.person.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.person.application.api.model.representation.PersonListRepresentation;

import java.util.List;

public interface PersonList extends Service {

    List<PersonListRepresentation> getAllPersons();
    PersonListRepresentation getPerson(Long personId);
    PersonListRepresentation deletePerson(Long personId);
    PersonListRepresentation enablePerson(Long personId);

}
