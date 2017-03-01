
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.service.EventRegistration;
import de.scisertec.event.client.model.request.EventRegistrationRequest;
import de.scisertec.event.client.model.request.ShortEventRegistrationRequest;
import de.scisertec.event.client.resource.api.RegistrationResource;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class RegistrationResourceBean implements RegistrationResource {

    @Inject
    EventRegistration eventRegistration;

    @Logging(out = true)
    public PersonRepresentation register(@Valid EventRegistrationRequest request) {
        return eventRegistration.registerForEvent(request);
    }

    @Logging(out = true)
    public PersonRepresentation registerShort(@Valid ShortEventRegistrationRequest request) {
        return eventRegistration.registerForShortEvent(request);
    }
}
