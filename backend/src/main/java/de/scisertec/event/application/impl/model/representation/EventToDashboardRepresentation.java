package de.scisertec.event.application.impl.model.representation;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventDashboardRepresentation;
import de.scisertec.event.application.api.model.representation.feature.EventFeatureDashboardRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.EventFeatureToDashboardRepresentation;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.feature.DeadlineFeature;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class EventToDashboardRepresentation extends AbstractRepresentation implements EventDashboardRepresentation {

    Long id;
    String name;
    String identifier;
    Boolean published;
    Boolean shortVersion;
    String link;
    String status;

    Long participants;
    Long participantsPaid;
    String registrationEnd;
    List<String> eventModules;
    List<EventFeatureDashboardRepresentation> eventFeatures = new ArrayList<>();

    public EventToDashboardRepresentation(Event event) {
        this.name = event.name();
        this.identifier = event.identifier();
        this.id = event.getId();
        this.published = event.published();
        this.link = event.registrationLink();
        this.status = event.eventStatus().name();
        this.participants = event.validParticipantCount();
        this.shortVersion = event.shortVersion();
        this.participantsPaid = event.participants().stream().filter(participation -> participation.paid() && participation.active()).count();
        this.eventModules = event.eventModules().stream().map(Enum::name).collect(Collectors.toList());
        this.registrationEnd = LocalDate.fromCalendarFields(((DeadlineFeature) (event.eventFeatures(EventFeatureCategory.DEADLINE, EventFeatureType.REGISTRATION)
                .stream().findFirst().get())).deadline()).toString();
        event.eventFeatures().stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId())).forEach((eventFeature -> {
            if (eventFeature.eventFeatureType().equals(EventFeatureType.PARTICIPANT_STATUS)) {
                EventParticipationFeature eventParticipationFeature = (EventParticipationFeature) eventFeature;
                this.eventFeatures.add(new EventFeatureToDashboardRepresentation(event, eventParticipationFeature));
            }
            if (eventFeature.eventFeatureType().equals(EventFeatureType.SELECTION)) {
                EventParticipationFeature eventParticipationFeature = (EventParticipationFeature) eventFeature;
                this.eventFeatures.add(new EventFeatureToDashboardRepresentation(event, eventParticipationFeature));
            }
            if (eventFeature.eventFeatureType().equals(EventFeatureType.DINNER_COMBO)) {
                EventParticipationFeature eventParticipationFeature = (EventParticipationFeature) eventFeature;
                this.eventFeatures.add(new EventFeatureToDashboardRepresentation(event, eventParticipationFeature));
            }
        }));
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Boolean getPublished() {
        return published;
    }

    @Override
    public Boolean getShortVersion() {
        return shortVersion;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Long getParticipants() {
        return participants;
    }

    @Override
    public String getRegistrationEnd() {
        return registrationEnd;
    }

    @Override
    public List<String> getEventModules() {
        return eventModules;
    }

    @Override
    public List<EventFeatureDashboardRepresentation> getEventFeatures() {
        return eventFeatures;
    }

    @Override
    public Long getParticipantsPaid() {
        return participantsPaid;
    }
}
