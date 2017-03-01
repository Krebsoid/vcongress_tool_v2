package de.scisertec.event.application.impl.model.representation;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventSelectionListRepresentation;
import de.scisertec.event.domain.model.EventSelection;

public class EventSelectionToListRepresentation extends AbstractRepresentation implements EventSelectionListRepresentation {

    Long eventFeatureId;
    String eventFeature;
    String value;
    Boolean fixed;

    public EventSelectionToListRepresentation(EventSelection eventSelection) {
        this.value = eventSelection.value();
        this.eventFeatureId = eventSelection.eventFeature().getId();
        this.eventFeature = eventSelection.eventFeature().eventFeatureType().name();
        this.fixed = eventSelection.fixed();
    }

    @Override
    public Long getEventFeatureId() {
        return eventFeatureId;
    }

    @Override
    public String getEventFeature() {
        return eventFeature;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Boolean getFixed() {
        return fixed;
    }
}
