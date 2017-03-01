package de.scisertec.person.domain.model;

import javax.persistence.Entity;

@Entity
public class InvoiceAddress extends Address {

    String fullName = "";
    String institute = "";

    public String fullName() {
        return fullName;
    }
    public InvoiceAddress fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String institute() {
        return institute;
    }
    public InvoiceAddress institute(String institute) {
        this.institute = institute;
        return this;
    }

    @Override
    public InvoiceAddress self() {
        return this;
    }

    public InvoiceAddress copy() {
        InvoiceAddress address = new InvoiceAddress();
        address.fullName = fullName;
        address.institute = institute;
        address.street = street;
        address.city = city;
        address.state = state;
        address.zipCode = zipCode;
        address.country = country;
        return address;
    }
}
