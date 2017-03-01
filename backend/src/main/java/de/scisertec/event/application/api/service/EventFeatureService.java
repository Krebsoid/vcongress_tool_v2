package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.feature.EventParticipationFeatureUpdateCommand;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;

public interface EventFeatureService extends Service {

    EventFeatureRepresentation updateEventFeature(Long eventFeatureId,
                                                               EventParticipationFeatureUpdateCommand eventParticipationFeatureUpdateCommand);

    EventFeatureRepresentation updateParticipationEventFeature(Long eventFeatureId,
                                                               EventParticipationFeatureUpdateCommand eventParticipationFeatureUpdateCommand);

    EventFeatureRepresentation removeEventFeature(Long eventFeatureId);
}
