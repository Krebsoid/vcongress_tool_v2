
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.event.client.model.request.EventRegistrationRequest;
import de.scisertec.event.client.model.request.ShortEventRegistrationRequest;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("event/registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RegistrationResource extends Resource {

    @POST
    PersonRepresentation register(@Valid EventRegistrationRequest request);

    @POST
    @Path("short")
    PersonRepresentation registerShort(@Valid ShortEventRegistrationRequest request);

}
