package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventItem;

public class ParticipantStatusDeletion extends DomainEvent<ParticipantStatusDeletion> {

    String name;

    public static ParticipantStatusDeletion create(EventItem eventItem) {
        ParticipantStatusDeletion participantStatusDeletion = new ParticipantStatusDeletion();
        participantStatusDeletion.name = eventItem.name();
        participantStatusDeletion.fireEvent();
        return participantStatusDeletion;
    }

    @Override
    public String loggerStamp() {
        return "PARTICIPANT-STATUS-DELETION: " + name;
    }

    @Override
    public ParticipantStatusDeletion self() {
        return this;
    }

}
