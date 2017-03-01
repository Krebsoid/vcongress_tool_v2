package de.scisertec.person.application.api.service;


import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.person.application.api.model.command.PersonRegistrationCommand;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;

public interface Registration extends Service {

    PersonRepresentation register(PersonRegistrationCommand personRegistrationCommand);

}
