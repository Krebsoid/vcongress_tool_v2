package de.scisertec.person.application.impl.model.representation;


import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.person.application.api.model.representation.AddressRepresentation;
import de.scisertec.person.application.api.model.representation.CountryRepresentation;
import de.scisertec.person.domain.model.Address;

public class AddressToRepresentation extends AbstractRepresentation implements AddressRepresentation {

    String street;
    String city;
    String zipCode;
    String state;
    CountryRepresentation country;

    public AddressToRepresentation(Address address) {
        this.street = address.street();
        this.city = address.city();
        this.zipCode = address.zipCode();
        this.state = address.state();
        if(address.country() != null) {
            this.country = new CountryToRepresentation(address.country());
        }
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public CountryRepresentation getCountry() {
        return country;
    }
}
