package de.scisertec.person.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.person.application.api.model.command.InvoiceAddressEditCommand;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceAddressEditRequest extends AddressEditRequest implements InvoiceAddressEditCommand, Request {

    @NotEmpty
    String fullName;
    String institute;

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getInstitute() {
        return institute;
    }
}
