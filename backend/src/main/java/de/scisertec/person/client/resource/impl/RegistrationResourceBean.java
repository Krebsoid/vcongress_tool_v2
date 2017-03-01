
package de.scisertec.person.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.application.api.service.Registration;
import de.scisertec.person.client.model.PersonRegistrationRequest;
import de.scisertec.person.client.resource.api.RegistrationResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class RegistrationResourceBean implements RegistrationResource {

    @Inject
    Registration registration;

    @Logging(out = true)
    public PersonRepresentation register(@Valid PersonRegistrationRequest request) {
        return registration.register(request);
    }

}
