package de.scisertec.person.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.person.application.api.model.command.ContactEditCommand;
import de.scisertec.person.application.api.model.command.OccupationEditCommand;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactEditRequest implements ContactEditCommand, Request {

    @NotEmpty
    String phone;
    String fax;

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getFax() {
        return fax;
    }
}
