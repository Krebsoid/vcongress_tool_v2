package de.scisertec.person.application.api.model.command;

public interface InvoiceAddressEditCommand extends AddressEditCommand {

    String getFullName();
    String getInstitute();

}
