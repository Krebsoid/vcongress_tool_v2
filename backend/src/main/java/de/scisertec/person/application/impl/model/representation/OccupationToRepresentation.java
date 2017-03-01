package de.scisertec.person.application.impl.model.representation;


import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.person.application.api.model.representation.OccupationRepresentation;
import de.scisertec.person.domain.model.Occupation;

public class OccupationToRepresentation extends AbstractRepresentation implements OccupationRepresentation {

    String institute;
    String department;
    String position;

    public OccupationToRepresentation(Occupation occupation) {
        this.institute = occupation.institute();
        this.department = occupation.department();
        this.position = occupation.position();
    }

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
