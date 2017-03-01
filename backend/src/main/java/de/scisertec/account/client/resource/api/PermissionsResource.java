package de.scisertec.account.client.resource.api;

import de.scisertec.account.application.api.model.representation.GroupRepresentation;
import de.scisertec.account.application.api.model.representation.RoleRepresentation;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("account/permissions")
@Produces(MediaType.APPLICATION_JSON)
public interface PermissionsResource extends Resource {

    @GET
    @Path("groups")
    @NoCache
    List<GroupRepresentation> getGroups();

    @GET
    @Path("{group}/roles")
    @NoCache
    List<RoleRepresentation> getRoles(@PathParam("group") String group);

    @Path("{user}/{group}/{role}")
    @PUT
    UserStateRepresentation addRole(@PathParam("user") Long id, @PathParam("group") String group, @PathParam("role") String role);

    @Path("{user}/{group}/{role}")
    @DELETE
    UserStateRepresentation removeRole(@PathParam("user") Long id, @PathParam("group") String group, @PathParam("role") String role);

}
