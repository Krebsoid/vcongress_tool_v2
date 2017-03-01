package de.scisertec.person.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;

@Entity
public class Contact extends DomainModel<Contact> {

    String phone = "";
    String fax = "";

    public String phone() {
        return phone;
    }
    public Contact phone(String phone) {
        this.phone = phone;
        return this;
    }

    public String fax() {
        return fax != null ? fax : "";
    }
    public Contact fax(String fax) {
        this.fax = fax;
        return this;
    }

    @Override
    public Contact self() {
        return this;
    }
}
