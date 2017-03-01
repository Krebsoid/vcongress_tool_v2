
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.event.application.api.model.representation.ParticipationRepresentation;
import de.scisertec.event.client.model.request.DinnerSaveRequest;
import de.scisertec.event.client.model.request.ParticipantNoteUpdateRequest;
import de.scisertec.event.client.model.request.ParticipationStatusSaveRequest;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("participation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ParticipationResource extends Resource {


    @POST
    @Path("status")
    ParticipationRepresentation addStatus(@Valid ParticipationStatusSaveRequest participationSaveRequest);

    @POST
    @Path("dinner")
    ParticipationRepresentation addDinner(@Valid DinnerSaveRequest dinnerSaveRequest);

    @GET
    @NoCache
    ParticipationRepresentation get();


    @POST
    @Path("{id}/status")
    ParticipationRepresentation addStatus(@PathParam("id") Long id, @Valid ParticipationStatusSaveRequest participationSaveRequest);

    @POST
    @Path("{id}/dinner")
    ParticipationRepresentation addDinner(@PathParam("id") Long id, @Valid DinnerSaveRequest dinnerSaveRequest);

    @POST
    @Path("{id}/enable")
    ParticipationRepresentation enable(@PathParam("id") Long id);

    @POST
    @Path("{id}/cancel")
    ParticipationRepresentation cancel(@PathParam("id") Long id);

    @POST
    @Path("{id}/note")
    ParticipationRepresentation updateNote(@PathParam("id") Long id, @Valid ParticipantNoteUpdateRequest participantNoteUpdateRequest);

    @POST
    @Path("{id}/save")
    ParticipationRepresentation save(@PathParam("id") Long id, @QueryParam("method") String method);

    @GET
    @NoCache
    @Path("{id}")
    ParticipationRepresentation get(@PathParam("id") Long id);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") Long id);

}
