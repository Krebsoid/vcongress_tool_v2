package de.scisertec.person.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.person.application.api.model.command.CountryEditCommand;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryAdminEditRequest implements CountryEditCommand, Request {

    String isoCode;

    @Override
    public String getIsoCode() {
        return isoCode;
    }
}
