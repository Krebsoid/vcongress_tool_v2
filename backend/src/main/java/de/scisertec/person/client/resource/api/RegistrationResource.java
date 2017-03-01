
package de.scisertec.person.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.client.model.PersonRegistrationRequest;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RegistrationResource extends Resource {


    @POST
    PersonRepresentation register(@Valid PersonRegistrationRequest request);

}
