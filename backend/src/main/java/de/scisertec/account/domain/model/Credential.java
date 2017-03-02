package de.scisertec.account.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "systemcredential")
public class Credential extends DomainModel<Credential> {


    String username;

    String password;

    String salt;


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
        this.salt = RandomStringUtils.randomAlphanumeric(20);
        this.password = DigestUtils.sha512Hex(password + salt);
        return this;
    }

    public Credential randomPassword() {
        return password(RandomStringUtils.randomAlphanumeric(8));
    }

    public Credential self() {
        return this;
    }
}
