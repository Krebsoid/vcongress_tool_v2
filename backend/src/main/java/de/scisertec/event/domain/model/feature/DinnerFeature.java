package de.scisertec.event.domain.model.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.DinnerFeatureToRepresentation;
import de.scisertec.event.domain.model.EventItem;
import de.scisertec.event.domain.model.EventItemType;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.EventSelection;
import de.scisertec.event.domain.model.selection.DinnerSelection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DinnerFeature extends EventParticipationFeature {

    @OneToOne(mappedBy = "eventFeature", cascade = CascadeType.ALL)
    EventItem eventItem;

    public EventItem eventItem() {
        return eventItem;
    }

    @Override
    public EventFeatureType eventFeatureType() {
        return EventFeatureType.DINNER;
    }

    @Override
    public EventSelection defaultSelection() {
        DinnerSelection dinnerSelection = new DinnerSelection();
        dinnerSelection.eventFeature(this);
        dinnerSelection.checked(false);
        return dinnerSelection;
    }

    @Override
    public Boolean hasDefaultSelection() {
        return true;
    }

    @Override
    public void init() {
        eventItem = new EventItem();
        eventItem.eventFeature(this);
        eventItem.eventItemType(EventItemType.DINNER);
    }

    @Override
    public Boolean singleFeature() {
        return Boolean.FALSE;
    }

    @Override
    public EventFeatureRepresentation getRepresentation() {
        return new DinnerFeatureToRepresentation(this);
    }
}
