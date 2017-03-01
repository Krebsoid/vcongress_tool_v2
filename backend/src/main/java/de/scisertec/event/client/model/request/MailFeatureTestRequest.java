package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.feature.MailFeatureTestCommand;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MailFeatureTestRequest implements MailFeatureTestCommand, Request {

    @NotEmpty
    @Email
    String receiver;

    @Override
    public String getReceiver() {
        return receiver;
    }

}
