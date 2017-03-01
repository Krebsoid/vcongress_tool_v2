package de.scisertec.account.domain.model;

public class Nobody extends User {

    public Credential credential() {
        return new Credential().username("Nobody");
    }

    public Boolean isUser() { return false; }

}
