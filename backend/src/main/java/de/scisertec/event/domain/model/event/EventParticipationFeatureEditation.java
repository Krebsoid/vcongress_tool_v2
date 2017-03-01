package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.EventParticipationFeature;

public class EventParticipationFeatureEditation extends DomainEvent<EventParticipationFeatureEditation> {

    String eventIdentifier;
    String label;
    EventFeature eventFeature;

    public EventFeature eventFeature() {
        return eventFeature;
    }


    public static EventParticipationFeatureEditation create(EventParticipationFeature eventFeature) {
        EventParticipationFeatureEditation participantStatusCreation = new EventParticipationFeatureEditation();
        participantStatusCreation.label = eventFeature.label();
        participantStatusCreation.eventIdentifier = eventFeature.event().identifier();
        participantStatusCreation.eventFeature = eventFeature;
        participantStatusCreation.fireEvent();
        return participantStatusCreation;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-FEATURE-EDITATION: " + eventFeature.eventFeatureType().name() + " - " + eventIdentifier + " - " + label;
    }

    @Override
    public EventParticipationFeatureEditation self() {
        return this;
    }

}
