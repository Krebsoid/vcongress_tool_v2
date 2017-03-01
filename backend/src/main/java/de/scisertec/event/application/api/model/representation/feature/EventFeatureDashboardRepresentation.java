package de.scisertec.event.application.api.model.representation.feature;

import java.util.List;

public interface EventFeatureDashboardRepresentation {

    String getLabel();
    List<EventItemDashboardRepresentation> getEventItems();

}
