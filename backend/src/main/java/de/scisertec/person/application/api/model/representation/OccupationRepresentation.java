package de.scisertec.person.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

public interface OccupationRepresentation extends Representation {

    String getInstitute();
    String getDepartment();
    String getPosition();

}
