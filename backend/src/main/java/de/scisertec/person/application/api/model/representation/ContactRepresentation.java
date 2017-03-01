package de.scisertec.person.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

public interface ContactRepresentation extends Representation {

    String getPhone();
    String getFax();

}
