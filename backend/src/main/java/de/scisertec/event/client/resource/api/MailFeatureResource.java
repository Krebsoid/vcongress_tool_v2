
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.client.model.request.MailFeatureTestRequest;
import de.scisertec.event.client.model.request.MailFeatureUpdateRequest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("eventfeature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MailFeatureResource extends Resource {

    @PUT
    @Path("{eventFeatureId}/mail")
    EventFeatureRepresentation updateMailFeature(@PathParam("eventFeatureId") Long eventFeatureId,
                                                @Valid MailFeatureUpdateRequest mailFeatureUpdateRequest);

    @POST
    @Path("{eventFeatureId}/mail/test")
    void testMailFeature(@PathParam("eventFeatureId") Long eventFeatureId,
                         @Valid MailFeatureTestRequest mailFeatureTestRequest);

}
