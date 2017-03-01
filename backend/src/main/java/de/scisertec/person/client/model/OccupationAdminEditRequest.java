package de.scisertec.person.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.person.application.api.model.command.OccupationEditCommand;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OccupationAdminEditRequest implements OccupationEditCommand, Request {

    String institute;
    String department;
    String position;

    @Override
    public String getInstitute() {
        return institute;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public String getPosition() {
        return position;
    }
}
