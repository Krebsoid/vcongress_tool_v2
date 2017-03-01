package de.scisertec.event.domain.model.selection;

import de.scisertec.event.domain.model.EventItem;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.EventSelection;
import de.scisertec.event.domain.model.feature.DinnerFeature;

import javax.persistence.Entity;

@Entity
public class DinnerSelection extends EventSelection {

    Boolean checked;

    public Boolean checked() {
        return checked;
    }
    public DinnerSelection checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public String value() {
        return checked ? "Ja" : "Nein";
    }

    @Override
    public Boolean isValid() {
        if(((EventParticipationFeature)eventFeature()).required()) {
            return checked;
        } else {
            return true;
        }
    }

    @Override
    public Boolean isSelected(EventItem eventItem) {
        return checked && eventItem.equals(((DinnerFeature)eventFeature()).eventItem());
    }

}
