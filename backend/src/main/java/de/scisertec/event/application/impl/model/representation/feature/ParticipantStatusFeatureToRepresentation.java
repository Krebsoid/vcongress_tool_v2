package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.application.api.model.representation.feature.ParticipantStatusFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventItemToRepresentation;
import de.scisertec.event.application.impl.model.representation.EventParticipationFeatureToRepresentation;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.feature.ParticipantStatusFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipantStatusFeatureToRepresentation extends EventParticipationFeatureToRepresentation implements ParticipantStatusFeatureRepresentation {

    List<EventItemRepresentation> status = new ArrayList<>();

    public ParticipantStatusFeatureToRepresentation(ParticipantStatusFeature participantStatusFeature) {
        super(participantStatusFeature);
        this.status = participantStatusFeature.status().stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .map(EventItemToRepresentation::new).collect(Collectors.toList());
    }


    @Override
    public List<EventItemRepresentation> getStatus() {
        return status;
    }

    public void makeParticipationDependant(Participation participation) {
        this.status.stream().forEach(eventItemRepresentation -> eventItemRepresentation.makeParticipationDependant(participation));
    }
}
