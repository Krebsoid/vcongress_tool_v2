package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;

import java.util.List;

public interface ParticipationRepresentation extends Representation {

    Long getId();
    String getEventIdentifier();
    String getDateOfRegistration();
    Boolean getActive();
    Boolean getComplete();
    Boolean getPaid();
    String getNote();
    List<EventSelectionRepresentation> getEventSelections();
    PersonRepresentation getPerson();

    void makeParticipationDependant(Participation participation);

}
