package de.scisertec.event.client.resource.api;

import de.scisertec.event.application.api.model.representation.EventLogoRepresentation;
import de.scisertec.event.client.model.request.EventLogoUploadRequest;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("event")
public interface EventLogoResource {

    @POST
    @Path("{id}/logo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    EventLogoRepresentation uploadEventLogo(@PathParam("id") String identifier, @Valid @MultipartForm EventLogoUploadRequest eventLogoUploadRequest);

    @DELETE
    @Path("{id}/logo")
    void removeEventLogo(@PathParam("id") String identifier);

}
