package de.scisertec.account.domain.model;

import de.scisertec.account.domain.model.event.*;
import de.scisertec.core.domain.model.base.Deletable;
import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.*;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "systemuser")
public class User extends DomainModel<User> implements Deletable<User>, HttpSessionBindingListener {

    @OneToOne(cascade = CascadeType.ALL)
    Credential credential;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<Relationship> relationships = new HashSet<>();

    Boolean enabled = Boolean.TRUE;
    Boolean deleted = Boolean.FALSE;

    String autoLoginToken = UUID.randomUUID().toString();
    String remoteEditingToken = UUID.randomUUID().toString();

    @Transient
    Boolean remoteLoggedIn = Boolean.FALSE;

    public Credential credential() {
        return credential;
    }

    public Set<Relationship> relationships() {
        return relationships;
    }

    public String autoLoginToken() {
        return autoLoginToken;
    }

    public String remoteEditingToken() {
        return remoteEditingToken;
    }

    public Boolean remoteLoggedIn() {
        return remoteLoggedIn;
    }

    public User remoteLoggedIn(Boolean remoteLoggedIn) {
        this.remoteLoggedIn = remoteLoggedIn;
        return this;
    }

    public Boolean enabled() {
        return enabled;
    }

    public User enabled(Boolean enabled) {
        this.enabled = enabled;
        UserEnable.create(this);
        return this;
    }

    public Boolean deleted() {
        return deleted;
    }

    public User deleted(Boolean deleted) {
        this.deleted = deleted;
        UserDeletion.create(this);
        return this;
    }

    public String name() {
        return credential().username();
    }

    public User name(String name) {
        credential().username(name);
        return this;
    }

    public User password(String password) {
        credential().password(password);
        return this;
    }

    public User randomPassword() {
        credential().randomPassword();
        return this;
    }

    public User addRole(Group group, Role role) {
        Optional<Relationship> relationshipOptional = relationships.stream()
                .filter(r -> r.group().equals(group))
                .findFirst();
        if(relationshipOptional.isPresent()) {
            Relationship relationship = relationshipOptional.get();
            relationship.addRole(role);
        } else {
            Relationship newRelationship = Relationship.create(group, Collections.singletonList(role).stream().collect(Collectors.toSet()));
            this.addRelationship(newRelationship);
        }
        AddRole.create(this, role);
        return this;
    }

    public User removeRole(Group group, Role role) {
        Optional<Relationship> relationshipOptional = relationships.stream()
                .filter(r -> r.group().equals(group))
                .findFirst();
        if(relationshipOptional.isPresent()) {
            Relationship relationship = relationshipOptional.get();
            relationship.removeRole(role);
            if(relationship.roles().isEmpty()) {
                this.removeRelationship(relationship);
            }
            RemoveRole.create(this, role);
        }
        return this;
    }

    public User addRelationship(Relationship relationship) {
        this.relationships.add(relationship);
        return this;
    }

    public User addRelationship(Group group) {
        this.relationships.add(Relationship.create(group));
        return this;
    }

    public User addRelationship(Group group, Set<Role> roles) {
        this.relationships.add(Relationship.create(group, roles));
        return this;
    }

    public User removeRelationship(Relationship relationship) {
        this.relationships.remove(relationship);
        return this;
    }

    public User credential(Credential credential) {
        this.credential = credential;
        return this;
    }


    public User self() {
        return this;
    }
    public Boolean isUser() { return true; }


    public Boolean credentialCheck(Login login) {
        return credentialCheck(login.username(), login.password());
    }

    public Boolean credentialCheck(String username, String password) {
        return credential.username().equals(username) &&
                credential.password().equals(password);
    }

    public Boolean hasGroup(Group group) {
        return hasGroup(group.identifier());
    }

    public Boolean hasGroup(String group) {
        return this.relationships.stream()
                .anyMatch(relationship -> relationship.group.identifier().equals(group));
    }

    public Boolean hasRole(Group group, Role role) {
        return hasRole(group.identifier(), role.name());
    }

    public Boolean hasRole(Group group, String role) {
        return hasRole(group.identifier(), role);
    }

    public Boolean hasRole(String group, String role) {
        return this.relationships.stream()
                .anyMatch(relationship ->
                        relationship.group.identifier().equals(group) &&
                        relationship.roles().stream()
                                .anyMatch(r -> r.name().equals(role)));
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        Set<User> logins = (Set<User>) event.getSession().getServletContext().getAttribute("logins");
        if(logins == null) {
            event.getSession().getServletContext().setAttribute("logins", new HashSet<User>());
        }
        logins = (Set<User>) event.getSession().getServletContext().getAttribute("logins");
        logins.add(this);
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        Set<User> logins = (Set<User>) event.getSession().getServletContext().getAttribute("logins");
        if(logins == null) {
            event.getSession().getServletContext().setAttribute("logins", new HashSet<User>());
        }
        logins = (Set<User>) event.getSession().getServletContext().getAttribute("logins");
        logins.remove(this);
    }
}
