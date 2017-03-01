package de.scisertec.person.application.api.model.representation;

import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.core.application.api.model.representation.Representation;

public interface PersonListRepresentation extends Representation {

    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    Boolean getEnabled();

    UserStateRepresentation getUser();

}
