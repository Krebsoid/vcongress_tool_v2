package de.scisertec.event.application.api.model.representation;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.event.domain.model.Participation;

public interface EventFeatureRepresentation extends Representation {

    Long getId();
    String getEventFeatureType();
    String getEventFeatureCategory();

    Boolean getPublic();

    void makeParticipationDependant(Participation participation);
    void setUser(User user);

}
