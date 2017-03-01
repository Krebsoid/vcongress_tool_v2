package de.scisertec.account.client.resource.impl;

import de.scisertec.account.application.api.model.representation.GroupRepresentation;
import de.scisertec.account.application.api.model.representation.RoleRepresentation;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.application.api.service.Permissions;
import de.scisertec.account.client.resource.api.PermissionsResource;
import de.scisertec.core.infrastructure.qualifier.Logging;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import java.util.List;

public class PermissionsResourceBean implements PermissionsResource {

    @Inject
    Permissions permissions;

    public List<GroupRepresentation> getGroups() {
        return permissions.getGroups();
    }

    public List<RoleRepresentation> getRoles(String group) {
        return permissions.getRoles(group);
    }

    @Logging
    public UserStateRepresentation addRole(@PathParam("id") Long id, @PathParam("group") String group, @PathParam("role") String role) {
        return permissions.addRole(id, group, role);
    }

    @Logging
    public UserStateRepresentation removeRole(@PathParam("id") Long id, @PathParam("group") String group, @PathParam("role") String role) {
        return permissions.removeRole(id, group, role);
    }
}
