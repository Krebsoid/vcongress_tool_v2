package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.feature.EventFeatureCreationCommand;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventFeatureCreationRequest implements EventFeatureCreationCommand, Request {

    @NotEmpty
    String featureType;

    @Override
    public String getFeatureType() {
        return featureType;
    }

}
