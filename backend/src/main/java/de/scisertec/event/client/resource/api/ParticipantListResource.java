
package de.scisertec.event.client.resource.api;

import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventDashboardRepresentation;
import de.scisertec.event.application.api.model.representation.EventRepresentation;
import de.scisertec.event.application.api.model.representation.ParticipationListRepresentation;
import de.scisertec.event.application.api.model.representation.ParticipationRepresentation;
import de.scisertec.event.client.model.request.EventCreationRequest;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("event")
public interface ParticipantListResource extends Resource {

    @GET
    @NoCache
    @Path("{id}/participants")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<ParticipationListRepresentation> getParticipants(@PathParam("id") String identifier);

    @GET
    @NoCache
    @Path("{id}/participants/export.csv")
    @Produces("text/csv")
    byte[] getParticipantsAsCSV(@PathParam("id") String identifier);


}
