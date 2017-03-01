package de.scisertec.person.application.impl.model.representation;


import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.person.application.api.model.representation.ContactRepresentation;
import de.scisertec.person.domain.model.Contact;

public class ContactToRepresentation extends AbstractRepresentation implements ContactRepresentation {

    String phone;
    String fax;

    public ContactToRepresentation(Contact contact) {
        this.phone = contact.phone();
        this.fax = contact.fax();
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getFax() {
        return fax;
    }
}
