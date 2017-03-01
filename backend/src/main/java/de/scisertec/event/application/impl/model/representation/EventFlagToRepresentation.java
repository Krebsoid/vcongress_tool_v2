package de.scisertec.event.application.impl.model.representation;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventFlagRepresentation;
import de.scisertec.event.domain.model.EventFlag;

public class EventFlagToRepresentation extends AbstractRepresentation implements EventFlagRepresentation {

    Long id;
    String name;

    public EventFlagToRepresentation(EventFlag eventFlag) {
        this.name = eventFlag.name();
        this.id = eventFlag.getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
