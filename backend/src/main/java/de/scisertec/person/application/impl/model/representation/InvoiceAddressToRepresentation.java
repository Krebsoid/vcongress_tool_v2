package de.scisertec.person.application.impl.model.representation;


import de.scisertec.person.application.api.model.representation.InvoiceAddressRepresentation;
import de.scisertec.person.domain.model.InvoiceAddress;

public class InvoiceAddressToRepresentation extends AddressToRepresentation implements InvoiceAddressRepresentation {

    String fullName;
    String institute;

    public InvoiceAddressToRepresentation(InvoiceAddress address) {
        super(address);
        this.fullName = address.fullName();
        this.institute = address.institute();
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getInstitute() {
        return institute;
    }
}
