
package de.scisertec.person.client.resource.impl;

import de.scisertec.account.application.api.security.LoggedIn;
import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.application.api.service.PersonService;
import de.scisertec.person.client.model.PersonAdminEditRequest;
import de.scisertec.person.client.model.InvoiceAddressEditRequest;
import de.scisertec.person.client.model.PersonEditRequest;
import de.scisertec.person.client.resource.api.PersonEditResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@LoggedIn
@ApplicationScoped
public class PersonEditResourceBean implements PersonEditResource {

    @Inject
    PersonService personService;

    @Logging(out = true)
    public PersonRepresentation editPerson(@Valid PersonEditRequest personEditRequest) {
        return personService.editPerson(personEditRequest);
    }

    @Override
    public PersonRepresentation editPersonInvoiceAddress(@Valid InvoiceAddressEditRequest invoiceAddressEditRequest) {
        return personService.editPersonInvoiceAddress(invoiceAddressEditRequest);
    }

    @Logging(out = true)
    public PersonRepresentation editPerson(Long personId, @Valid PersonEditRequest personEditRequest) {
        return personService.editPerson(personId, personEditRequest);
    }

    @Override
    public PersonRepresentation editAdmin(Long personId, @Valid PersonAdminEditRequest personEditRequest) {
        return personService.editPerson(personId, personEditRequest);
    }

    @Logging(out = true)
    public PersonRepresentation getPerson(Long personId) {
        return personService.getPerson(personId);
    }

    @Logging(out = true)
    public PersonRepresentation activeState() {
        return personService.activeState();
    }
}
