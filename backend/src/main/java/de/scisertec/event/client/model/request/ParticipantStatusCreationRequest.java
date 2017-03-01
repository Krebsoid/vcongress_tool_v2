package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.ParticipantStatusCreationCommand;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantStatusCreationRequest extends EventItemCreationRequest implements ParticipantStatusCreationCommand, Request {

    String startDate;
    String endDate;

    Boolean earlyBird;
    Boolean earlyBirdActive;

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public Boolean getEarlyBird() {
        return earlyBird != null ? earlyBird : false;
    }

    @Override
    public Boolean getEarlyBirdActive() {
        return earlyBirdActive != null ? earlyBirdActive : false;
    }

}
