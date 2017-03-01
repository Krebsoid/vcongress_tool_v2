package de.scisertec.event.domain.model.event;

import de.scisertec.event.domain.model.EventItem;
import de.scisertec.event.domain.model.selection.ParticipantStatusSelection;

import javax.persistence.Entity;
import javax.persistence.Transient;

public class ParticipantStatusSave extends EventSelectionSave {

    ParticipantStatusSelection participantStatusSelection;

    public static ParticipantStatusSave create(ParticipantStatusSelection participantStatusSelection) {
        ParticipantStatusSave eventSelectionSave = new ParticipantStatusSave();
        eventSelectionSave.eventSelection = participantStatusSelection;
        eventSelectionSave.participantStatusSelection = participantStatusSelection;
        eventSelectionSave.fireEvent();
        return eventSelectionSave;
    }

    @Override
    public String loggerStamp() {
        EventItem eventItem = participantStatusSelection.eventItem();
        if(eventItem != null) {
            return super.loggerStamp() + " - " + eventItem.name();
        } else {
            return super.loggerStamp() + " - REMOVED";
        }

    }

    @Override
    public ParticipantStatusSave self() {
        return this;
    }

}
