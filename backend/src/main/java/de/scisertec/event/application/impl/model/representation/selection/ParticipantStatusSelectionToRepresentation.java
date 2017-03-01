package de.scisertec.event.application.impl.model.representation.selection;

import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.model.representation.selection.ParticipantStatusSelectionRepresentation;
import de.scisertec.event.application.impl.model.representation.EventItemToRepresentation;
import de.scisertec.event.application.impl.model.representation.EventSelectionToRepresentation;
import de.scisertec.event.domain.model.selection.ParticipantStatusSelection;

public class ParticipantStatusSelectionToRepresentation extends EventSelectionToRepresentation implements ParticipantStatusSelectionRepresentation {

    EventItemRepresentation status;

    public ParticipantStatusSelectionToRepresentation(ParticipantStatusSelection participantStatusSelection) {
        super(participantStatusSelection);
        if(participantStatusSelection.eventItem() != null) {
            this.status = new EventItemToRepresentation(participantStatusSelection.eventItem());
        }
    }

    @Override
    public EventItemRepresentation getParticipantStatus() {
        return status;
    }
}
