package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;
import de.scisertec.event.domain.model.EventFeature;

import javax.persistence.Entity;
import javax.persistence.Transient;

public class EventFeatureCreation extends DomainEvent<EventFeatureCreation> {

    String eventIdentifier;
    EventFeature eventFeature;

    public EventFeature eventFeature() {
        return eventFeature;
    }

    public static EventFeatureCreation create(EventFeature eventFeature) {
        EventFeatureCreation participantStatusCreation = new EventFeatureCreation();
        participantStatusCreation.eventIdentifier = eventFeature.event().identifier();
        participantStatusCreation.eventFeature = eventFeature;
        participantStatusCreation.fireEvent();
        return participantStatusCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-FEATURE-CREATION: " + eventFeature.eventFeatureType().name() + " - " + eventIdentifier;
    }

    @Override
    public EventFeatureCreation self() {
        return this;
    }

}
