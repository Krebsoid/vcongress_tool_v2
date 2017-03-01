package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventFeature;

public class EventFeatureDeletion extends DomainEvent<EventFeatureDeletion> {

    String eventIdentifier;
    EventFeature eventFeature;

    public EventFeature eventFeature() {
        return eventFeature;
    }


    public static EventFeatureDeletion create(EventFeature eventFeature) {
        EventFeatureDeletion participantStatusCreation = new EventFeatureDeletion();
        participantStatusCreation.eventIdentifier = eventFeature.event().identifier();
        participantStatusCreation.eventFeature = eventFeature;
        participantStatusCreation.fireEvent();
        return participantStatusCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-FEATURE-DELETION: " + eventFeature.eventFeatureType().name() + " - " + eventIdentifier;
    }

    @Override
    public EventFeatureDeletion self() {
        return this;
    }

}
