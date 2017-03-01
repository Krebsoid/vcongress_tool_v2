package de.scisertec.event.domain.model.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.ParticipantStatusFeatureToRepresentation;
import de.scisertec.event.domain.model.EventItem;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.EventSelection;
import de.scisertec.event.domain.model.selection.ParticipantStatusSelection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class ParticipantStatusFeature extends EventParticipationFeature {

    @OneToMany(mappedBy = "eventFeature", cascade = CascadeType.ALL)
    List<EventItem> participantStatusList = new ArrayList<>();

    @Enumerated
    EventFeatureType eventFeatureType = EventFeatureType.SELECTION;

    public List<EventItem> status() {
        return participantStatusList;
    }

    @Override
    public EventFeatureType eventFeatureType() {
        return eventFeatureType;
    }

    public ParticipantStatusFeature eventFeatureType(EventFeatureType eventFeatureType) {
        this.eventFeatureType = eventFeatureType;
        return this;
    }

    @Override
    public EventSelection defaultSelection() {
        Optional<EventItem> defaultStatus = participantStatusList.stream().filter(eventItem -> eventItem.checkForEventFlag("DEFAULT_STATUS")).findAny();
        if(defaultStatus.isPresent()) {
            ParticipantStatusSelection participantStatusSelection = new ParticipantStatusSelection();
            participantStatusSelection.eventFeature(this);
            participantStatusSelection.eventItem(defaultStatus.get());
            return participantStatusSelection;
        } else {
            ParticipantStatusSelection participantStatusSelection = new ParticipantStatusSelection();
            participantStatusSelection.eventFeature(this);
            return participantStatusSelection;
        }
    }

    @Override
    public Boolean hasDefaultSelection() {
        return true;
    }

    @Override
    public void init() {

    }

    @Override
    public Boolean singleFeature() {
        if(eventFeatureType().equals(EventFeatureType.SELECTION)) {
            return Boolean.FALSE;
        } else if(eventFeatureType().equals(EventFeatureType.PARTICIPANT_STATUS)) {
            return Boolean.TRUE;
        } else {
            return super.singleFeature();
        }
    }

    @Override
    public EventFeatureRepresentation getRepresentation() {
        return new ParticipantStatusFeatureToRepresentation(this);
    }

    @Override
    public ParticipantStatusFeature self() {
        return this;
    }
}
