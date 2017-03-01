package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.DinnerUpdateCommand;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DinnerUpdateRequest implements DinnerUpdateCommand, Request {

    @NotEmpty
    String name;

    Integer limit;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

}
