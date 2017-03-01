package de.scisertec.person.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.person.application.api.model.command.AddressEditCommand;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressAdminEditRequest implements AddressEditCommand, Request {

    String street;
    String city;
    String zipCode;
    String state;

    @Valid
    CountryEditRequest country;

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
    public CountryEditRequest getCountry() {
        return country;
    }
}
