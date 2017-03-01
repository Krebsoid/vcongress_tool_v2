package de.scisertec.account.application.impl.model.representation;

import de.scisertec.account.application.api.model.representation.RelationshipRepresentation;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.domain.model.User;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserToStateRepresentation extends AbstractRepresentation implements UserStateRepresentation {

    Long id;
    String name;
    Boolean activated;
    Boolean remoteLoggedIn;
    Set<RelationshipRepresentation> relationships = new HashSet<>();

    public UserToStateRepresentation(User user) {
        this.id = user.getId();
        this.name = user.credential().username();
        this.activated = user.enabled();
        this.remoteLoggedIn =user.remoteLoggedIn();
        this.relationships = user.relationships().stream().map(RelationshipToRepresentation::new).collect(Collectors.toSet());
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Boolean getActivated() {
        return activated;
    }

    @Override
    public Boolean getRemoteLoggedIn() {
        return remoteLoggedIn;
    }

    public Set<RelationshipRepresentation> getRelationships() {
        return relationships;
    }

}
