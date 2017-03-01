package de.scisertec.person.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Address extends DomainModel<Address> {

    String street = "";
    String zipCode = "";
    String city = "";

    String state = "";

    @ManyToOne
    Country country;

    public String street() {
        return street;
    }
    public Address street(String street) {
        this.street = street;
        return this;
    }

    public String zipCode() {
        return zipCode;
    }
    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String city() {
        return city;
    }
    public Address city(String city) {
        this.city = city;
        return this;
    }

    public String state() {
        return state != null ? state : "";
    }
    public Address state(String state) {
        this.state = state;
        return this;
    }

    public Country country() {
        return country;
    }
    public Address country(Country country) {
        this.country = country;
        return this;
    }

    @Override
    public Address self() {
        return this;
    }
}
