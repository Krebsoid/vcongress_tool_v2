package de.scisertec.account.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

import java.util.Set;

public interface UserStateRepresentation extends Representation {

    Long getId();
    String getName();
    Boolean getActivated();
    Boolean getRemoteLoggedIn();
    Set<RelationshipRepresentation> getRelationships();

}
