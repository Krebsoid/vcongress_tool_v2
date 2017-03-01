
package de.scisertec.person.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.person.application.api.model.representation.CountryRepresentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("country")
public interface CountryResource extends Resource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CountryRepresentation> getCountries();

}
