package de.scisertec.account.application.impl.model.representation;

import de.scisertec.account.application.api.model.representation.GroupRepresentation;
import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.Role;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupToRepresentation extends AbstractRepresentation implements GroupRepresentation {

    String name;
    List<String> availableRoles;

    public GroupToRepresentation(Group group) {
        this.name = group.identifier();
        this.availableRoles = group.roles().stream().map(Role::name)
                .filter(roleName -> !roleName.equals("USER") && !roleName.equals("TEILNEHMER"))
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public List<String> getAvailableRoles() {
        return availableRoles;
    }
}
