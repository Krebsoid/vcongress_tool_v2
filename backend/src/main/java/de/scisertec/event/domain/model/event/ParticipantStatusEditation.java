package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventItem;

public class ParticipantStatusEditation extends DomainEvent<ParticipantStatusEditation> {

    String name;

    public static ParticipantStatusEditation create(EventItem eventItem) {
        ParticipantStatusEditation participantStatusEditation = new ParticipantStatusEditation();
        participantStatusEditation.name = eventItem.name();
        participantStatusEditation.fireEvent();
        return participantStatusEditation;
    }

    @Override
    public String loggerStamp() {
        return "PARTICIPANT-STATUS-EDITATION: " + name;
    }

    @Override
    public ParticipantStatusEditation self() {
        return this;
    }

}
