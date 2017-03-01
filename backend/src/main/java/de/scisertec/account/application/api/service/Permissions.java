package de.scisertec.account.application.api.service;

import de.scisertec.account.application.api.model.representation.GroupRepresentation;
import de.scisertec.account.application.api.model.representation.RoleRepresentation;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;

import java.util.List;

public interface Permissions {

    List<RoleRepresentation> getRoles(String group);
    List<GroupRepresentation> getGroups();

    UserStateRepresentation addRole(Long userId, String group, String role);
    UserStateRepresentation removeRole(Long userId, String group, String role);

}
