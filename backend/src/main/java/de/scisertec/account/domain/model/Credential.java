package de.scisertec.account.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;
import org.apache.commons.lang.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "systemcredential")
public class Credential extends DomainModel<Credential> {


    String username;

    String password;



    public String username() {
        return username;
    }

    public Credential username(String username) {
        this.username = username;
        return this;
    }

    public String password() {
        return password;
    }

    public Credential password(String password) {
        this.password = password;
        return this;
    }

    public Credential randomPassword() {
        this.password = RandomStringUtils.randomAlphanumeric(8);
        return this;
    }

    public Credential self() {
        return this;
    }
}
