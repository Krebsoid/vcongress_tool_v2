package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.EventFlagCreationCommand;
import de.scisertec.event.application.api.model.command.ParticipantStatusCreationCommand;
import de.scisertec.event.application.api.model.command.ParticipantStatusUpdateCommand;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;

import java.util.List;

public interface ParticipantStatusService extends Service {

    List<EventItemRepresentation> getAllStatus(Long eventFeatureId);
    EventItemRepresentation getStatus(Long eventFeatureId, Long participantStatusId);
    EventItemRepresentation addStatus(Long eventFeatureId,
                                              ParticipantStatusCreationCommand participantStatusCreationCommand);
    EventItemRepresentation addFlag(Long participantStatusId, EventFlagCreationCommand eventFlagCreationCommand);
    EventItemRepresentation removeFlag(Long participantStatusId, Long flagId);
    EventItemRepresentation editStatus(Long eventFeatureId, Long participantStatusId,
                                       ParticipantStatusCreationCommand participantStatusUpdateCommand);
    EventItemRepresentation removeStatus(Long eventFeatureId, Long participantStatusId);

}
