package de.scisertec.account.application.impl.model.representation;


import de.scisertec.account.application.api.model.representation.RelationshipRepresentation;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.domain.model.User;

import java.util.Set;

public class EmptyUserStateRepresentation extends UserToStateRepresentation implements UserStateRepresentation {
    public EmptyUserStateRepresentation(User user) {
        super(user);
    }

    public String getName() {
        return null;
    }

    @Override
    public Boolean getActivated() {
        return Boolean.FALSE;
    }

    public Set<RelationshipRepresentation> getRelationships() {
        return null;
    }

    public Long getId() {
        return null;
    }
}
