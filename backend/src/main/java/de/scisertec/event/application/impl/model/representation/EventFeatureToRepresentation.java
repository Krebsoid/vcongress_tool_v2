package de.scisertec.event.application.impl.model.representation;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.Participation;

public class EventFeatureToRepresentation extends AbstractRepresentation implements EventFeatureRepresentation {

    Long id;
    String eventFeatureType;
    String eventFeatureCategory;

    EventFeature eventFeature;

    Boolean disabled;

    User user;

    public EventFeatureToRepresentation(EventFeature eventFeature) {
        this.id = eventFeature.getId();
        this.eventFeature = eventFeature;
        this.eventFeatureType = eventFeature.eventFeatureType().name();
        this.eventFeatureCategory = eventFeature.eventFeatureCategory().name();
        this.disabled = false;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getEventFeatureType() {
        return eventFeatureType;
    }

    @Override
    public String getEventFeatureCategory() {
        return eventFeatureCategory;
    }

    @Override
    public Boolean getPublic() {
        return Boolean.TRUE;
    }

    @Override
    public void makeParticipationDependant(Participation participation) {
        this.disabled = eventFeature.disabled();
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected <T> T adminProperty(T property) {
        if(this.user == null) {
            return property;
        } else {
            if(this.user.hasRole("system", "ADMIN") ||
                    this.user.hasRole(this.eventFeature.event().identifier(), "ORGANISATOR")) {
                return property;
            } else {
                return null;
            }
        }
    }

}
