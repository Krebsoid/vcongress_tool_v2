package de.scisertec.person.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

public interface AddressRepresentation extends Representation {

    String getStreet();
    String getCity();
    String getZipCode();
    String getState();
    CountryRepresentation getCountry();

}
