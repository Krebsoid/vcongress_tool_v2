package de.scisertec.core.client.model.request;

import de.scisertec.core.application.api.model.command.IdAssignmentCommand;

import javax.validation.constraints.NotNull;

public class IdAssignmentRequest implements IdAssignmentCommand, Request {

    @NotNull
    Long id;

    @Override
    public Long getId() {
        return id;
    }
}
