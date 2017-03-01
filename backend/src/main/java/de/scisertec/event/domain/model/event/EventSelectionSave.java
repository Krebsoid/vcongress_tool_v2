package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventSelection;

public class EventSelectionSave extends DomainEvent<EventSelectionSave> {

    EventSelection eventSelection;

    public EventSelection eventSelection() {
        return eventSelection;
    }

    public static EventSelectionSave create(EventSelection eventSelection) {
        EventSelectionSave eventSelectionSave = new EventSelectionSave();
        eventSelectionSave.eventSelection = eventSelection;
        eventSelectionSave.fireEvent();
        return eventSelectionSave;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-SELECTION: " + eventSelection.eventFeature().eventFeatureType().name() + " - " + eventSelection.eventFeature().getId();
    }

    @Override
    public EventSelectionSave self() {
        return this;
    }

}
