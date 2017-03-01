package de.scisertec.event.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;

@Entity
public class EventFlag extends DomainModel<EventFlag> {

    String name;

    public static EventFlag create(String name) {
        EventFlag eventFlag = new EventFlag();
        eventFlag.name = name;
        return eventFlag;
    }

    public String name() {
        return name;
    }

    @Override
    public EventFlag self() {
        return this;
    }

}
