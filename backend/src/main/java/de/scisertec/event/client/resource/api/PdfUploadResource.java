package de.scisertec.event.client.resource.api;

import de.scisertec.event.client.model.request.PdfUploadRequest;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("event")
public interface PdfUploadResource {

    @POST
    @Path("{id}/license")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response uploadPdfDocument(@PathParam("id") String identifier, @Valid @MultipartForm PdfUploadRequest pdfUploadRequest);

}
