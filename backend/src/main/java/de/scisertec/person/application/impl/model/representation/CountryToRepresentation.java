package de.scisertec.person.application.impl.model.representation;


import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.person.application.api.model.representation.CountryRepresentation;
import de.scisertec.person.domain.model.Country;

public class CountryToRepresentation extends AbstractRepresentation implements CountryRepresentation {
    Long id;
    String name;
    String isoCode;

    public CountryToRepresentation(Country country) {
        this.id = country.getId();
        this.name = country.name();
        this.isoCode = country.isoCode();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIsoCode() {
        return isoCode;
    }
}
