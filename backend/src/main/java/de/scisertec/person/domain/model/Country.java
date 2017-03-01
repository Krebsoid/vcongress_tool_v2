package de.scisertec.person.domain.model;

import de.scisertec.core.domain.model.LocaleString;
import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Country extends DomainModel<Country> {

    @OneToOne(cascade = CascadeType.ALL)
    LocaleString name;
    String isoCode;

    public String name() {
        return name.value();
    }
    public Country name(LocaleString name) {
        this.name = name;
        return this;
    }
    public String isoCode() {
        return isoCode;
    }
    public Country isoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    @Override
    public Country self() {
        return this;
    }
}
