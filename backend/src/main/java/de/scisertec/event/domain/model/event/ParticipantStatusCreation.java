package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventItem;

public class ParticipantStatusCreation extends DomainEvent<ParticipantStatusCreation> {

    String name;

    public static ParticipantStatusCreation create(EventItem eventItem) {
        ParticipantStatusCreation participantStatusCreation = new ParticipantStatusCreation();
        participantStatusCreation.name = eventItem.name();
        participantStatusCreation.fireEvent();
        return participantStatusCreation;
    }

    @Override
    public String loggerStamp() {
        return "PARTICIPANT-STATUS-CREATION: " + name;
    }

    @Override
    public ParticipantStatusCreation self() {
        return this;
    }

}
