package de.scisertec.account.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "passtwordrecoverytoken")
public class PasswordRecoveryToken extends DomainModel<PasswordRecoveryToken> {

    String value;
    Boolean used;

    public static PasswordRecoveryToken generate() {
        PasswordRecoveryToken passwordRecoveryToken = new PasswordRecoveryToken().construct();
        passwordRecoveryToken.used = false;
        passwordRecoveryToken.value = UUID.randomUUID().toString();
        return passwordRecoveryToken;
    }

    public String value() {
        return value;
    }

    public Boolean used() {
        return used;
    }

    public void setUsed() {
        this.used = true;
        this.save();
    }

    @Override
    public PasswordRecoveryToken self() {
        return this;
    }
}
