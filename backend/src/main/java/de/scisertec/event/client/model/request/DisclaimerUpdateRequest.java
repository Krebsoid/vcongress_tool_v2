package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.feature.DisclaimerUpdateCommand;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisclaimerUpdateRequest implements DisclaimerUpdateCommand, Request {

    @NotEmpty
    String content;

    Integer index;

    @NotNull
    Boolean mandatory;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Boolean getMandatory() {
        return mandatory;
    }

    @Override
    public Integer getIndex() {
        return index;
    }
}
