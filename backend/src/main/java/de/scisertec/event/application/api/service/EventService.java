package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.EventCreationCommand;
import de.scisertec.event.application.api.model.command.feature.EventFeatureCreationCommand;
import de.scisertec.event.application.api.model.representation.*;

import java.util.List;

public interface EventService extends Service {

    EventRepresentation createEvent(EventCreationCommand eventCreationCommand);
    EventRepresentation createShortEvent(EventCreationCommand eventCreationCommand);
    EventRepresentation editEvent(String identifier, EventCreationCommand eventCreationCommand);
    void deleteEvent(String identifier);
    EventRepresentation getEvent(String identifier);
    List<EventDashboardRepresentation> getAllEventAsSummary();
    List<EventRepresentation> getAllEvents();

    List<ParticipationListRepresentation> getParticipants(String identifier);

    EventRepresentation setTestMode(String identifier, Boolean testMode);
    EventRepresentation setLicense(String identifier, String license);
    EventRepresentation addModule(String identifier, String module);
    EventRepresentation removeModule(String identifier, String module);
    EventRepresentation publishEvent(String identifier);
    EventRepresentation closeEvent(String identifier);
    EventRepresentation reopenEvent(String identifier);
    EventRepresentation archiveEvent(String identifier);

    EventFeatureRepresentation addEventFeature(String identifier, EventFeatureCreationCommand eventFeatureCreationCommand);

}
