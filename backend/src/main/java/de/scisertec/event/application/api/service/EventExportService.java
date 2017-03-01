package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;

public interface EventExportService extends Service {

    String getParticipantsAsCSV(String identifier);

}
