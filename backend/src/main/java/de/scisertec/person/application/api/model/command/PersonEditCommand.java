package de.scisertec.person.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface PersonEditCommand extends Command {

    String getTitle();
    String getGender();

    String getFirstName();
    String getLastName();
    String getEmail();

    AddressEditCommand getAddress();
    InvoiceAddressEditCommand getInvoiceAddress();
    OccupationEditCommand getOccupation();
    ContactEditCommand getContact();

}
