
package de.scisertec.event.client.resource.impl;

import de.scisertec.event.application.api.model.representation.EventLogoRepresentation;
import de.scisertec.event.application.api.service.EventLogoService;
import de.scisertec.event.client.model.request.EventLogoUploadRequest;
import de.scisertec.event.client.resource.api.EventLogoResource;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class EventLogoResourceBean implements EventLogoResource {

    @Inject
    EventLogoService eventLogoService;

    @Override
    public EventLogoRepresentation uploadEventLogo(String identifier, @Valid @MultipartForm EventLogoUploadRequest eventLogoUploadRequest) {
        return eventLogoService.uploadEventLogo(identifier, eventLogoUploadRequest);
    }

    @Override
    public void removeEventLogo(String identifier) {
        eventLogoService.removeEventLogo(identifier);
    }
}
