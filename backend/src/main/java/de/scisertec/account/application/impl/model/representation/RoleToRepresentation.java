package de.scisertec.account.application.impl.model.representation;

import de.scisertec.account.application.api.model.representation.RoleRepresentation;
import de.scisertec.account.domain.model.Role;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;

public class RoleToRepresentation extends AbstractRepresentation implements RoleRepresentation {

    String name;

    public RoleToRepresentation(Role role) {
        this.name = role.name();
    }

    public String getName() {
        return name;
    }
}
