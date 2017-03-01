package de.scisertec.account.application.impl.model.representation;

import de.scisertec.account.application.api.model.representation.GroupRepresentation;
import de.scisertec.account.application.api.model.representation.RelationshipRepresentation;
import de.scisertec.account.application.api.model.representation.RoleRepresentation;
import de.scisertec.account.domain.model.Relationship;
import de.scisertec.account.domain.model.Role;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RelationshipToRepresentation extends AbstractRepresentation implements RelationshipRepresentation {

    Long id;
    GroupRepresentation group;
    Set<RoleRepresentation> roles = new HashSet<RoleRepresentation>();

    public RelationshipToRepresentation(Relationship relationship) {
        this.id = relationship.getId();
        this.group = new GroupToRepresentation(relationship.group());
        this.roles = relationship.roles().stream().map(RoleToRepresentation::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public GroupRepresentation getGroup() {
        return group;
    }

    public Set<RoleRepresentation> getRoles() {
        return roles;
    }
}
