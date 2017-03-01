package de.scisertec.core.client.model.request;

import de.scisertec.core.application.api.model.command.MultipleIdAssignmentCommand;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class MultipleIdAssignmentRequest implements MultipleIdAssignmentCommand, Request {

    @NotEmpty
    List<Long> ids;

    @Override
    public List<Long> getIds() {
        return ids;
    }
}
