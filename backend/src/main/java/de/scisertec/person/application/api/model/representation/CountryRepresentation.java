package de.scisertec.person.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

public interface CountryRepresentation extends Representation {

    Long getId();
    String getName();
    String getIsoCode();

}
