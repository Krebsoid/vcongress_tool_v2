package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.EventRegistrationCommand;
import de.scisertec.event.application.api.model.command.ShortEventRegistrationCommand;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;

public interface EventRegistration extends Service {

    PersonRepresentation registerForEvent(EventRegistrationCommand eventRegistrationCommand);
    PersonRepresentation registerForShortEvent(ShortEventRegistrationCommand eventRegistrationCommand);

}
