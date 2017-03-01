
package de.scisertec.event.client.resource.impl;

import de.scisertec.event.application.api.service.PdfUploadService;
import de.scisertec.event.client.model.request.PdfUploadRequest;
import de.scisertec.event.client.resource.api.PdfUploadResource;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class PdfUploadResourceBean implements PdfUploadResource {

    @Inject
    PdfUploadService pdfUploadService;

    @Override
    public Response uploadPdfDocument(String identifier, @Valid @MultipartForm PdfUploadRequest pdfUploadRequest) {
        pdfUploadService.uploadPdfDocument(identifier, pdfUploadRequest);
        return Response.ok().build();
    }
}
