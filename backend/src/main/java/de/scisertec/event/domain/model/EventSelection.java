package de.scisertec.event.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;
import de.scisertec.event.domain.model.event.EventSelectionsFixed;
import de.scisertec.event.domain.model.event.EventSelectionsReleased;

import javax.persistence.*;

@Entity
public abstract class EventSelection extends DomainModel<EventSelection> {

    Boolean fixed = false;

    @ManyToOne(optional = false)
    EventFeature eventFeature;

    @ManyToOne(optional = false)
    Participation participation;

    public Boolean fixed() {
        return fixed != null ? fixed : false;
    }
    public EventSelection fixed(Boolean fixed) {
        this.fixed = fixed;
        return this;
    }

    public EventSelection fixed(EventSelectionsFixed eventSelectionFixed) {
        if(eventSelectionFixed.fixedFeatureList().containsKey(this.eventFeature().getId())) {
            this.fixed(true);
        }
        return this;
    }

    public EventSelection fixed(EventSelectionsReleased eventSelectionsReleased) {
        if(eventSelectionsReleased.fixedFeatureList().containsKey(this.eventFeature().getId())) {
            this.fixed(false);
        }
        return this;
    }

    public EventFeature eventFeature() {
        return eventFeature;
    }
    public EventSelection eventFeature(EventFeature eventFeature) {
        this.eventFeature = eventFeature;
        return this;
    }

    public Participation participation() {
        return participation;
    }
    public EventSelection participation(Participation participation) {
        this.participation = participation;
        return this;
    }

    public abstract String value();

    public abstract Boolean isValid();

    public abstract Boolean isSelected(EventItem eventItem);

    @Override
    public EventSelection self() {
        return this;
    }
}
