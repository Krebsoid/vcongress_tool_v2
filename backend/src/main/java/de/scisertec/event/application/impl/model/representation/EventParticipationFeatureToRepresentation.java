package de.scisertec.event.application.impl.model.representation;

import de.scisertec.event.application.api.model.representation.EventParticipationFeatureRepresentation;
import de.scisertec.event.domain.model.EventParticipationFeature;

public class EventParticipationFeatureToRepresentation extends EventFeatureToRepresentation implements EventParticipationFeatureRepresentation {

    String label;
    String description;
    Boolean required;

    public EventParticipationFeatureToRepresentation(EventParticipationFeature eventFeature) {
        super(eventFeature);
        this.label = eventFeature.label();
        this.description = eventFeature.description();
        this.required = eventFeature.required();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

}
