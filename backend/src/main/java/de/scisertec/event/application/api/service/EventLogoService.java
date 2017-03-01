package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.EventLogoUploadCommand;
import de.scisertec.event.application.api.model.representation.EventLogoRepresentation;

public interface EventLogoService extends Service {

    EventLogoRepresentation uploadEventLogo(String identifier, EventLogoUploadCommand eventLogoUploadCommand);
    void removeEventLogo(String identifier);

}
