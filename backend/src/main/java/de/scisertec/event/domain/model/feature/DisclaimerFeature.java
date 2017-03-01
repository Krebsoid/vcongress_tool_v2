package de.scisertec.event.domain.model.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.DisclaimerFeatureToRepresentation;
import de.scisertec.event.domain.model.EventFeature;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DisclaimerFeature extends EventFeature {

    @Column(length = 1024)
    String content;
    Boolean mandatory;

    @Column(name = "orderIndex")
    Integer index;

    public Boolean mandatory() {
        return mandatory;
    }
    public DisclaimerFeature mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public String content() {
        return content;
    }
    public DisclaimerFeature content(String content) {
        this.content = content;
        return this;
    }

    public Integer index() {
        return index;
    }
    public DisclaimerFeature index(Integer index) {
        this.index = index;
        return this;
    }

    @Override
    public EventFeatureType eventFeatureType() {
        return EventFeatureType.DISCLAIMER;
    }

    @Override
    public EventFeatureCategory eventFeatureCategory() {
        return EventFeatureCategory.REGISTRATION;
    }

    @Override
    public void init() {

    }

    @Override
    public EventFeatureRepresentation getRepresentation() {
        return new DisclaimerFeatureToRepresentation(this);
    }

    @Override
    public DisclaimerFeature self() {
        return this;
    }
}
