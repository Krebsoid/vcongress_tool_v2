package de.scisertec.event.domain.model.selection;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.event.domain.model.EventItem;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.EventSelection;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ParticipantStatusSelection extends EventSelection {

    @ManyToOne
    EventItem eventItem;

    @Inject
    @Transient
    @BundlePath("general")
    LocalePropertyManager localePropertyManager;

    public EventItem eventItem() {
        return eventItem;
    }
    public ParticipantStatusSelection eventItem(EventItem eventItem) {
        this.eventItem = eventItem;
        return this;
    }

    public String value() {
        if(eventItem != null) {
            if(eventItem().checkForEventFlag("EARLY_BIRD")) {
                this.construct();
                return eventItem.name() + " (" + localePropertyManager.getLocalizedProperty("app.general.event.early_bird") + ")";
            } else {
                return eventItem.name();
            }
        } else {
            return "";
        }
    }

    @Override
    public Boolean isValid() {
        if(((EventParticipationFeature)eventFeature()).required()) {
            return eventItem != null;
        } else {
            return true;
        }
    }

    @Override
    public Boolean isSelected(EventItem eventItem) {
        return eventItem.equals(this.eventItem);
    }

}
