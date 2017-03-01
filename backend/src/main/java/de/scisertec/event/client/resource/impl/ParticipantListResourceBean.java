
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.application.impl.helper.CsvHelper;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.ParticipationListRepresentation;
import de.scisertec.event.application.api.model.representation.ParticipationRepresentation;
import de.scisertec.event.application.api.service.EventExportService;
import de.scisertec.event.application.api.service.EventService;
import de.scisertec.event.client.resource.api.ParticipantListResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.util.List;

@ApplicationScoped
public class ParticipantListResourceBean implements ParticipantListResource {

    @Inject
    EventService eventService;

    @Inject
    EventExportService eventExportService;

    @Logging
    public List<ParticipationListRepresentation> getParticipants(String identifier) {
        return eventService.getParticipants(identifier);
    }

    @Logging
    public byte[] getParticipantsAsCSV(String identifier) {
        return CsvHelper.convertToByteArrayWithUTF8(eventExportService.getParticipantsAsCSV(identifier));
    }
}
