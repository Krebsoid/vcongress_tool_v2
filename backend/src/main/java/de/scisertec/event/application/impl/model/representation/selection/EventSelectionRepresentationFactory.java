package de.scisertec.event.application.impl.model.representation.selection;

import de.scisertec.event.application.api.model.representation.EventSelectionListRepresentation;
import de.scisertec.event.application.api.model.representation.EventSelectionRepresentation;
import de.scisertec.event.application.impl.model.representation.EventSelectionToListRepresentation;
import de.scisertec.event.application.impl.model.representation.EventSelectionToRepresentation;
import de.scisertec.event.domain.model.EventSelection;
import de.scisertec.event.domain.model.selection.DinnerSelection;
import de.scisertec.event.domain.model.selection.ParticipantStatusSelection;

public class EventSelectionRepresentationFactory {

    public static EventSelectionRepresentation toRepresentation(EventSelection eventSelection) {
        switch(eventSelection.eventFeature().eventFeatureType()) {
            case PARTICIPANT_STATUS: return new ParticipantStatusSelectionToRepresentation((ParticipantStatusSelection) eventSelection);
            case SELECTION: return new ParticipantStatusSelectionToRepresentation((ParticipantStatusSelection) eventSelection);
            case DINNER: return new DinnerSelectionToRepresentation((DinnerSelection) eventSelection);
            default: return new EventSelectionToRepresentation(eventSelection);
        }
    }

    public static EventSelectionListRepresentation toListRepresentation(EventSelection eventSelection) {
        switch(eventSelection.eventFeature().eventFeatureType()) {
            case PARTICIPANT_STATUS: return new ParticipantStatusSelectionToListRepresentation((ParticipantStatusSelection) eventSelection);
            case SELECTION: return new ParticipantStatusSelectionToListRepresentation((ParticipantStatusSelection) eventSelection);
            case DINNER: return new DinnerSelectionToListRepresentation((DinnerSelection) eventSelection);
            default: return new EventSelectionToListRepresentation(eventSelection);
        }
    }

}
