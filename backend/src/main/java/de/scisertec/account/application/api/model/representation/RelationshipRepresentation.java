package de.scisertec.account.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

import java.util.Set;

public interface RelationshipRepresentation extends Representation {

    Long getId();
    GroupRepresentation getGroup();
    Set<RoleRepresentation> getRoles();

}
