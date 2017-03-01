package de.scisertec.event.domain.model;

import de.scisertec.event.domain.model.feature.EventFeatureCategory;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public abstract class EventParticipationFeature extends EventFeature {

    String label;
    @Column(length = 3072)
    String description;
    @Column(name = "orderIndex")
    Integer index;

    Boolean required = Boolean.FALSE;

    public Boolean required() {
        return required != null ? required : Boolean.FALSE;
    }
    public EventParticipationFeature required(Boolean required) {
        this.required = required;
        return this;
    }

    public String label() {
        return label;
    }
    public EventParticipationFeature label(String label) {
        this.label = label;
        return this;
    }

    public String description() {
        return description;
    }
    public EventParticipationFeature description(String description) {
        this.description = description;
        return this;
    }

    public Integer index() {
        return index;
    }
    public EventParticipationFeature index(Integer index) {
        this.index = index;
        return this;
    }

    public EventFeatureCategory eventFeatureCategory() {
        return EventFeatureCategory.PARTICIPATION;
    }

    public abstract EventSelection defaultSelection();
    public abstract Boolean hasDefaultSelection();

    public abstract void init();

    @Override
    public EventParticipationFeature self() {
        return this;
    }
}
