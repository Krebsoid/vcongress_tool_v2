package de.scisertec.person.application.api.model.representation;

import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.person.domain.model.Gender;

public interface PersonRepresentation extends Representation {

    Long getId();
    String getTitle();
    Gender getGender();

    String getFirstName();
    String getLastName();

    UserStateRepresentation getUser();

    AddressRepresentation getAddress();
    InvoiceAddressRepresentation getInvoiceAddress();
    ContactRepresentation getContact();
    OccupationRepresentation getOccupation();

}
