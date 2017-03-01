package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;

import java.util.List;

public interface ParticipationListRepresentation extends Representation {

    Long getId();
    String getEventIdentifier();
    String getDateOfRegistration();
    Boolean getActive();
    Boolean getComplete();
    Boolean getPaid();
    String getNote();
    List<EventSelectionListRepresentation> getEventSelections();
    PersonRepresentation getPerson();

}
