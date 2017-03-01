
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.client.model.request.EventItemCreationRequest;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("eventfeature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DinnerComboResource extends Resource {

    @POST
    @Path("{eventFeatureId}/dinnercombo")
    EventFeatureRepresentation createDinner(@PathParam("eventFeatureId") Long eventFeatureId,
                                            @Valid EventItemCreationRequest dinnerUpdateRequest);

    @DELETE
    @Path("{eventFeatureId}/dinnercombo/{dinnerId}")
    EventFeatureRepresentation removeDinner(@PathParam("eventFeatureId") Long eventFeatureId,
                                            @PathParam("dinnerId") Long dinnerId);

}
