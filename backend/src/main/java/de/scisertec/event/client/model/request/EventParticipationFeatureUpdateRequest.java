package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.feature.EventParticipationFeatureUpdateCommand;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventParticipationFeatureUpdateRequest implements EventParticipationFeatureUpdateCommand, Request {

    @NotEmpty
    String label;

    String description;

    Boolean required;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

}
