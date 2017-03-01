package de.scisertec.person.application.api.service;

import de.scisertec.person.application.api.model.command.InvoiceAddressEditCommand;
import de.scisertec.person.application.api.model.command.PersonEditCommand;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;

public interface PersonService {

    PersonRepresentation getPerson(Long id);
    PersonRepresentation editPerson(PersonEditCommand personEditCommand);
    PersonRepresentation editPersonInvoiceAddress(InvoiceAddressEditCommand invoiceAddressEditCommand);
    PersonRepresentation editPerson(Long id, PersonEditCommand personEditCommand);
    PersonRepresentation activeState();

}
