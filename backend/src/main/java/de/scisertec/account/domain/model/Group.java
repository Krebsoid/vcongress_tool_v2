package de.scisertec.account.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "systemgroup")
public class Group extends DomainModel<Group> {

    String identifier;
    String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<Role>();

    @OneToMany(mappedBy = "group")
    Set<Relationship> relationships = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    Role defaultRole;

    public Set<Relationship> relationships() {
        return relationships;
    }

    public String identifier() {
        return identifier;
    }

    public String name() {
        return name;
    }

    public Set<Role> roles() {
        return roles;
    }

    public Role defaultRole() {
        return defaultRole;
    }

    public Group name(String name) {
        this.name = name;
        return this;
    }

    public Role role(String name) {
        Optional<Role> foundRole = roles().stream().filter(role -> role.name.equals(name)).findFirst();
        if(foundRole.isPresent()) {
            return foundRole.get();
        } else {
            return null;
        }
    }

    public Group identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public Group defaultRole(Role role) {
        this.defaultRole = role;
        return this;
    }

    public Group addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public Group addRole(String role) {
        this.addRole(Role.create(role));
        return this;
    }

    public Group removeRole(Role role) {
        this.roles.remove(role);
        return this;
    }

    public Group removeRole(String role) {
        this.roles.removeIf(r -> r.name().equals(role));
        return this;
    }

    public Group self() {
        return this;
    }
}
