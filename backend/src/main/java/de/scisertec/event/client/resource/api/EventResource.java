
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.event.application.api.model.representation.EventDashboardRepresentation;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventRepresentation;
import de.scisertec.event.client.model.request.*;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EventResource extends Resource {

    @NoCache
    @POST
    EventRepresentation create(@Valid EventCreationRequest eventCreationRequest);

    @NoCache
    @POST
    @Path("short")
    EventRepresentation createShort(@Valid EventCreationRequest eventCreationRequest);

    @PUT
    @Path("{id}")
    EventRepresentation edit(@PathParam("id") String identifier, @Valid EventUpdateRequest eventCreationRequest);

    @PUT
    @Path("short/{id}")
    EventRepresentation editShort(@PathParam("id") String identifier, @Valid EventUpdateRequest eventCreationRequest);

    @DELETE
    @Path("{id}")
    Response delete(@PathParam("id") String identifier);

    @GET
    @NoCache
    @Path("summary")
    List<EventDashboardRepresentation> getAllAsSummary();

    @GET
    @NoCache
    @Path("{id}")
    EventRepresentation get(@PathParam("id") String identifier);

    @GET
    @NoCache
    List<EventRepresentation> getAll();


    @POST
    @Path("{id}/license/{license}")
    EventRepresentation license(@PathParam("id") String identifier, @PathParam("license") String license);

    @PUT
    @Path("{id}/module/{module}")
    EventRepresentation addModule(@PathParam("id") String identifier, @PathParam("module") String module);

    @DELETE
    @Path("{id}/module/{module}")
    EventRepresentation removeModule(@PathParam("id") String identifier, @PathParam("module") String module);

    @POST
    @Path("{id}/publish")
    EventRepresentation publish(@PathParam("id") String identifier);

    @POST
    @Path("{id}/archive")
    EventRepresentation archive(@PathParam("id") String identifier);

    @POST
    @Path("{id}/test/on")
    EventRepresentation activateTestMode(@PathParam("id") String identifier);

    @POST
    @Path("{id}/test/off")
    EventRepresentation deactivateTestMode(@PathParam("id") String identifier);

    @POST
    @Path("{id}/close")
    EventRepresentation close(@PathParam("id") String identifier);

    @POST
    @Path("{id}/reopen")
    EventRepresentation reopen(@PathParam("id") String identifier);

    @POST
    @Path("{id}/eventfeature")
    EventFeatureRepresentation addEventFeature(@PathParam("id") String identifier,
                                               @Valid EventFeatureCreationRequest eventFeatureCreationRequest);

}
