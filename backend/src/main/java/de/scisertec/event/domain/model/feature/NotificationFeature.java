package de.scisertec.event.domain.model.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.NotificationFeatureToRepresentation;
import de.scisertec.event.domain.model.EventFeature;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class NotificationFeature extends EventFeature {

    @Column(length = 2048)
    String notification;

    @Enumerated
    EventFeatureType eventFeatureType = EventFeatureType.INDEX;

    @Override
    public EventFeatureType eventFeatureType() {
        return eventFeatureType;
    }

    public NotificationFeature eventFeatureType(EventFeatureType eventFeatureType) {
        this.eventFeatureType = eventFeatureType;
        return this;
    }

    public String notification() {
        return notification;
    }
    public NotificationFeature notification(String notification) {
        this.notification = notification;
        return this;
    }


    public EventFeatureCategory eventFeatureCategory() {
        return EventFeatureCategory.TEXT;
    }

    @Override
    public void init() {

    }

    @Override
    public Boolean singleFeature() {
        return Boolean.FALSE;
    }

    @Override
    public EventFeatureRepresentation getRepresentation() {
        return new NotificationFeatureToRepresentation(this);
    }

    @Override
    public NotificationFeature self() {
        return this;
    }
}
