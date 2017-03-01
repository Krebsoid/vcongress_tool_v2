package de.scisertec.event.client.model.request;

import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.eventitem.ParticipationStatusSaveCommand;

import javax.validation.constraints.NotNull;

public class ParticipationStatusSaveRequest implements ParticipationStatusSaveCommand, Request {

    @NotNull
    Long eventItemId;

    @NotNull
    Long eventFeatureId;

    @Override
    public Long getEventItemId() {
        return eventItemId;
    }

    @Override
    public Long getEventFeatureId() {
        return eventFeatureId;
    }

}
