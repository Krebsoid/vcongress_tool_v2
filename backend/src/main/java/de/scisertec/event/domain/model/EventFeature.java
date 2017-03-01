package de.scisertec.event.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;

import javax.persistence.*;
import java.util.Set;

@Entity
public abstract class EventFeature extends DomainModel<EventFeature> {

    @ManyToOne(optional = false)
    Event event;

    @OneToMany(mappedBy = "eventFeature",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<EventSelection> eventSelections;

    Boolean disabled = false;

    public Set<EventSelection> getEventSelections() {
        return eventSelections;
    }

    public Boolean disabled() {
        return disabled;
    }
    public EventFeature disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public Event event() {
        return event;
    }
    public EventFeature event(Event event) {
        this.event = event;
        return this;
    }

    public abstract EventFeatureType eventFeatureType();
    public abstract EventFeatureCategory eventFeatureCategory();

    public abstract void init();

    public abstract EventFeatureRepresentation getRepresentation();

    public Boolean singleFeature() {
        return Boolean.TRUE;
    }

    @Override
    public EventFeature self() {
        return this;
    }
}
