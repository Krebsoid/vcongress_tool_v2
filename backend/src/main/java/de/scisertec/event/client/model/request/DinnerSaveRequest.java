package de.scisertec.event.client.model.request;

import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.eventitem.DinnerSaveCommand;

import javax.validation.constraints.NotNull;

public class DinnerSaveRequest implements DinnerSaveCommand, Request {

    @NotNull
    Boolean checked;

    @NotNull
    Long eventFeatureId;

    @Override
    public Boolean getChecked() {
        return checked;
    }

    @Override
    public Long getEventFeatureId() {
        return eventFeatureId;
    }

}
