package de.scisertec.event.application.impl.model.representation;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventSelectionRepresentation;
import de.scisertec.event.application.api.model.representation.ParticipationRepresentation;
import de.scisertec.event.application.impl.model.representation.selection.EventSelectionRepresentationFactory;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.person.application.api.model.representation.PersonRepresentation;
import de.scisertec.person.application.impl.model.representation.PersonToRepresentation;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

public class ParticipationToRepresentation extends AbstractRepresentation implements ParticipationRepresentation {

    Long id;
    String eventIdentifier;
    List<EventSelectionRepresentation> eventSelections;
    PersonRepresentation person;
    String dateOfRegistration;
    Boolean active;
    Boolean paid;
    Boolean complete;
    String note;

    public ParticipationToRepresentation(Participation participation) {
        this.id = participation.getId();
        this.eventIdentifier = participation.event().identifier();
        this.eventSelections = participation.eventSelections().values().stream()
                .sorted((o1, o2) -> o1.eventFeature().getId().compareTo(o2.eventFeature().getId()))
                .map(EventSelectionRepresentationFactory::toRepresentation).collect(Collectors.toList());
        this.paid = participation.paid();
        this.complete = participation.complete();
        this.person = new PersonToRepresentation(participation.person());
        this.dateOfRegistration = LocalDate.fromCalendarFields(participation.dateOfRegistration()).toString();
        this.active = participation.active();
        this.note = participation.note();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getEventIdentifier() {
        return eventIdentifier;
    }

    @Override
    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public Boolean getComplete() {
        return complete;
    }

    @Override
    public Boolean getPaid() {
        return paid;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public List<EventSelectionRepresentation> getEventSelections() {
        return eventSelections;
    }

    @Override
    public PersonRepresentation getPerson() {
        return person;
    }

    @Override
    public void makeParticipationDependant(Participation participation) {
        this.eventSelections.stream().forEach(eventSelectionRepresentation -> eventSelectionRepresentation.getEventFeature().makeParticipationDependant(participation));
    }
}
