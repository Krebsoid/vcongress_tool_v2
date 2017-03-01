package de.scisertec.event.client.model.request;

import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.ParticipantStatusUpdateCommand;
import org.hibernate.validator.constraints.NotEmpty;

public class ParticipantStatusUpdateRequest implements ParticipantStatusUpdateCommand, Request {

    @NotEmpty
    String name;

    @Override
    public String getName() {
        return name;
    }

}
