package de.scisertec.account.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "systemrole")
public class Role extends DomainModel<Role> {

    String name;

    public static Role create(String name) {
        Role role = new Role();
        role.name = name;
        return role;
    }

    public String name() {
        return name;
    }

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public Role self() {
        return this;
    }

}
