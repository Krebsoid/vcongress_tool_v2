package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.event.application.api.model.representation.feature.DeadlineFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventFeatureToRepresentation;
import de.scisertec.event.domain.model.feature.DeadlineFeature;
import org.joda.time.LocalDate;

public class DeadlineFeatureToRepresentation extends EventFeatureToRepresentation implements DeadlineFeatureRepresentation {

    String deadline;
    Boolean deadlineExpired;

    public DeadlineFeatureToRepresentation(DeadlineFeature deadlineFeature) {
        super(deadlineFeature);
        this.deadline = deadlineFeature.deadline() != null ? LocalDate.fromCalendarFields(deadlineFeature.deadline()).toString() : "";
        this.deadlineExpired = deadlineFeature.isDeadlineExpired();
    }


    @Override
    public String getDeadline() {
        return deadline;
    }

    @Override
    public Boolean isDeadlineExpired() {
        return deadlineExpired;
    }
}
