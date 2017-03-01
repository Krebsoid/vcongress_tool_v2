package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.event.application.api.model.representation.feature.EventFeatureDashboardRepresentation;

import java.util.List;

public interface EventDashboardRepresentation extends Representation {

    Long getId();
    String getName();
    String getIdentifier();
    Boolean getPublished();
    Boolean getShortVersion();
    String getLink();
    String getStatus();

    Long getParticipants();
    Long getParticipantsPaid();

    String getRegistrationEnd();

    List<String> getEventModules();

    List<EventFeatureDashboardRepresentation> getEventFeatures();

}
