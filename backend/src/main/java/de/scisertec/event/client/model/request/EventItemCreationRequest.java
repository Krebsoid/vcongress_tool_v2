package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.EventItemCreationCommand;
import de.scisertec.event.application.api.model.command.ParticipantStatusCreationCommand;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventItemCreationRequest implements EventItemCreationCommand, Request {

    @NotEmpty
    String name;

    String description;

    Integer limit;

    BigDecimal cost;
    Integer tax;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public Integer getTax() {
        return tax;
    }

}
