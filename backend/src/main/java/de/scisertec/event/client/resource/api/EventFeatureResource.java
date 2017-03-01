
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.client.model.request.EventParticipationFeatureUpdateRequest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("eventfeature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EventFeatureResource extends Resource {

    @PUT
    @Path("{eventFeatureId}")
    EventFeatureRepresentation updateEventFeature(@PathParam("eventFeatureId") Long eventFeatureId,
                                                  @Valid EventParticipationFeatureUpdateRequest eventFeatureUpdateRequest);

    @PUT
    @Path("participation/{eventFeatureId}")
    EventFeatureRepresentation updateParticipationEventFeature(@PathParam("eventFeatureId") Long eventFeatureId,
                                                  @Valid EventParticipationFeatureUpdateRequest eventFeatureUpdateRequest);

    @DELETE
    @Path("{eventFeatureId}")
    EventFeatureRepresentation removeEventFeature(@PathParam("eventFeatureId") Long eventFeatureId);
}
