package de.scisertec.event.domain.model.feature;

import de.scisertec.core.application.impl.helper.CalendarHelper;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.DeadlineFeatureToRepresentation;
import de.scisertec.event.domain.model.EventFeature;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class DeadlineFeature extends EventFeature {

    Calendar deadline;

    @Enumerated
    EventFeatureType eventFeatureType;

    @Override
    public EventFeatureType eventFeatureType() {
        return eventFeatureType;
    }

    public DeadlineFeature eventFeatureType(EventFeatureType eventFeatureType) {
        this.eventFeatureType = eventFeatureType;
        return this;
    }

    public Calendar deadline() {
        return deadline;
    }
    public DeadlineFeature deadline(Calendar deadline) {
        this.deadline = deadline;
        return this;
    }
    public DeadlineFeature deadline(String deadline) {
        this.deadline = CalendarHelper.stringToCalendar(deadline);
        return this;
    }

    public Boolean isDeadlineExpired() {
        Calendar today = new GregorianCalendar();
        return deadline == null || deadline.before(today);
    }

    public EventFeatureCategory eventFeatureCategory() {
        return EventFeatureCategory.DEADLINE;
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
        return new DeadlineFeatureToRepresentation(this);
    }

    @Override
    public DeadlineFeature self() {
        return this;
    }
}
