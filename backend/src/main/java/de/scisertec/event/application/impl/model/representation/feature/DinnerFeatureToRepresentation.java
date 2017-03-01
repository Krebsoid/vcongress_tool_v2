package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.model.representation.feature.DinnerFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventItemToRepresentation;
import de.scisertec.event.application.impl.model.representation.EventParticipationFeatureToRepresentation;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.feature.DinnerFeature;

public class DinnerFeatureToRepresentation extends EventParticipationFeatureToRepresentation implements DinnerFeatureRepresentation {

    EventItemRepresentation eventItem;

    public DinnerFeatureToRepresentation(DinnerFeature dinnerFeature) {
        super(dinnerFeature);
        this.eventItem = new EventItemToRepresentation(dinnerFeature.eventItem());
    }

    @Override
    public EventItemRepresentation getEventItem() {
        return eventItem;
    }

    @Override
    public void makeParticipationDependant(Participation participation) {
        this.eventItem.makeParticipationDependant(participation);
    }
}
