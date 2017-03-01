package de.scisertec.account.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "systemrelationship")
public class Relationship extends DomainModel<Relationship> {

    @ManyToOne
    Group group;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();

    public static Relationship create(Group group) {
        Relationship relationship = new Relationship();
        relationship.group(group).addRole(relationship.group().defaultRole());
        return relationship;
    }

    public static Relationship create(Group group, Set<Role> roles) {
        Relationship relationship = new Relationship();
        relationship.group(group).addRoles(roles);
        return relationship;
    }


    public Set<Role> roles() {
        return roles;
    }

    public Group group() {
        return group;
    }

    public Relationship group(Group group) {
        this.group = group;
        return this;
    }

    public Relationship addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public Relationship addRoles(Set<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public Relationship removeRole(Role role) {
        this.roles.remove(role);
        return this;
    }

    public Relationship self() {
        return this;
    }

}
