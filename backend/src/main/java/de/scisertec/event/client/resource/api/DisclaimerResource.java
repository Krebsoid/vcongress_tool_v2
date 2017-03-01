
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.client.model.request.DisclaimerUpdateRequest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("eventfeature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DisclaimerResource extends Resource {

    @PUT
    @Path("{eventFeatureId}/disclaimer")
    EventFeatureRepresentation updateDisclaimer(@PathParam("eventFeatureId") Long eventFeatureId,
                                            @Valid DisclaimerUpdateRequest dinnerUpdateRequest);

}
