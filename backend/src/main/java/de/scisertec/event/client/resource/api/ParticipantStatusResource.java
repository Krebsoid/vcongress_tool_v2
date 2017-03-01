
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.client.model.request.EventFlagCreationRequest;
import de.scisertec.event.client.model.request.ParticipantStatusCreationRequest;
import de.scisertec.event.client.model.request.ParticipantStatusUpdateRequest;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("eventfeature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ParticipantStatusResource extends Resource {

    @GET
    @Path("{eventFeatureId}/participantstatus/{participantStatusId}")
    @Logging
    EventItemRepresentation getStatus(@PathParam("eventFeatureId") Long eventFeatureId,
                                                       @PathParam("participantStatusId") Long participantStatusId);

    @GET
    @NoCache
    @Path("{id}/participantstatus")
    List<EventItemRepresentation> getAllStatus(@PathParam("id") Long eventFeatureId);

    @POST
    @Path("{id}/participantstatus")
    EventItemRepresentation addStatus(@PathParam("id") Long eventFeatureId,
                                              @Valid ParticipantStatusCreationRequest participantStatusCreationRequest);

    @POST
    @Path("{eventFeatureId}/participantstatus/{participantStatusId}/flag")
    EventItemRepresentation addFlag(@PathParam("eventFeatureId") Long eventFeatureId,
                                            @PathParam("participantStatusId") Long participantStatusId,
                                            @Valid EventFlagCreationRequest flagCreationRequest);
    @DELETE
    @Path("{eventFeatureId}/participantstatus/{participantStatusId}/flag/{flagId}")
    EventItemRepresentation removeFlag(@PathParam("eventFeatureId") Long eventFeatureId,
                                            @PathParam("participantStatusId") Long participantStatusId,
                                            @PathParam("flagId") Long flagId);

    @PUT
    @Path("{eventFeatureId}/participantstatus/{participantStatusId}")
    EventItemRepresentation updateStatus(@PathParam("eventFeatureId") Long eventFeatureId,
                                              @PathParam("participantStatusId") Long participantStatusId,
                                              @Valid ParticipantStatusCreationRequest participantStatusUpdateRequest);

    @DELETE
    @Path("{eventFeatureId}/participantstatus/{participantStatusId}")
    EventItemRepresentation removeStatus(@PathParam("eventFeatureId") Long eventFeatureId, @PathParam("participantStatusId") Long participantStatusId);

}
