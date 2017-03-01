package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;
import de.scisertec.event.domain.model.EventSelection;

import javax.persistence.Entity;
import javax.persistence.Transient;

public class EventSelectionDeletion extends DomainEvent<EventSelectionDeletion> {

    EventSelection eventSelection;

    public EventSelection eventSelection() {
        return eventSelection;
    }


    public static EventSelectionDeletion create(EventSelection eventSelection) {
        EventSelectionDeletion eventSelectionDeletion = new EventSelectionDeletion();
        eventSelectionDeletion.eventSelection = eventSelection;
        eventSelectionDeletion.fireEvent();
        return eventSelectionDeletion;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-SELECTION-DELETION: " + eventSelection.eventFeature().eventFeatureType().name() + " - " + eventSelection.eventFeature().getId();
    }

    @Override
    public EventSelectionDeletion self() {
        return this;
    }

}
