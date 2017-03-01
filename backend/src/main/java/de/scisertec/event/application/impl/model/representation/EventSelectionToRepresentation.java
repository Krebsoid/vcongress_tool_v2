package de.scisertec.event.application.impl.model.representation;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventSelectionRepresentation;
import de.scisertec.event.domain.model.EventSelection;

public class EventSelectionToRepresentation extends AbstractRepresentation implements EventSelectionRepresentation {

    EventFeatureRepresentation eventFeature;
    String value;
    Boolean fixed;

    public EventSelectionToRepresentation(EventSelection eventSelection) {
        this.value = eventSelection.value();
        this.eventFeature = eventSelection.eventFeature().getRepresentation();
        this.fixed = eventSelection.fixed();
    }

    @Override
    public EventFeatureRepresentation getEventFeature() {
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
