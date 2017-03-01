
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.client.model.request.EventItemCreationRequest;
import de.scisertec.event.client.model.request.ParticipantStatusCreationRequest;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("eventfeature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DinnerResource extends Resource {

    @GET
    @NoCache
    @Path("{eventFeatureId}/dinner")
    EventItemRepresentation getEventItem(@PathParam("eventFeatureId") Long eventFeatureId);

    @PUT
    @Path("{eventFeatureId}/dinner")
    EventItemRepresentation updateEventItem(@PathParam("eventFeatureId") Long eventFeatureId,
                                         @Valid EventItemCreationRequest dinnerUpdateRequest);

}
