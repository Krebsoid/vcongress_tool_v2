package de.scisertec.account.client.resource.api;

import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.client.model.request.LoginRequest;
import de.scisertec.core.client.resource.api.Resource;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("authorization")
public interface AuthorizationResource extends Resource {

    @Path("login")
    @POST
    UserStateRepresentation login(@Valid LoginRequest loginRequest);

    @Path("auto-login")
    @POST
    UserStateRepresentation autoLogin(@QueryParam("token") String autoLoginToken);

    @Path("remote-login")
    @POST
    UserStateRepresentation remoteLogin(@QueryParam("token") String remoteLoginToken);

    @Path("remote-login")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getRemoteLoginLink();

    @Path("logout")
    @POST
    void logout();

    @GET
    @NoCache
    UserStateRepresentation state();

}
