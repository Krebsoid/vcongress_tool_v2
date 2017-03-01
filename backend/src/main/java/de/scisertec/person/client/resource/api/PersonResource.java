
package de.scisertec.person.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.person.application.api.model.representation.PersonListRepresentation;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PersonResource extends Resource {


    @GET
    @NoCache
    List<PersonListRepresentation> getAllPersons();

    @GET
    @NoCache
    @Path("{id}")
    PersonListRepresentation getPerson(@PathParam("id") Long personId);

    @POST
    @Path("{id}/enable")
    PersonListRepresentation enablePerson(@PathParam("id") Long personId);

    @DELETE
    @Path("{id}")
    PersonListRepresentation deletePerson(@PathParam("id") Long personId);

}
