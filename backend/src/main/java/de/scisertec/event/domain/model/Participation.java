package de.scisertec.event.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.selection.ParticipantStatusSelection;
import de.scisertec.payment.domain.model.ParticipationTransaction;
import de.scisertec.person.domain.model.Person;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.*;

@Entity
public class Participation extends DomainModel<Participation> {

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    Person person;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Event event;

    Boolean active = true;
    Calendar dateOfRegistration = new GregorianCalendar();
    String note;

    Boolean paid = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participation", fetch = FetchType.LAZY)
    Map<Long, EventSelection> eventSelectionList = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "participation", fetch = FetchType.LAZY)
    List<ParticipationTransaction> participationTransactionList = new ArrayList<>();

    public static Participation create(Person person, Event event) {
        Participation participation = new Participation();
        participation.person = person;
        participation.event = event;
        return participation;
    }

    public Map<Long, EventSelection> eventSelections() {
        return eventSelectionList;
    }
    public Boolean hasEventSelection(EventItem eventItem) {
        return eventSelections().values().stream().anyMatch(eventSelection -> eventSelection.isSelected(eventItem));
    }
    public Calendar dateOfRegistration() {
        return dateOfRegistration;
    }


    public Boolean active() {
        return active;
    }
    public Participation active(Boolean active) {
        this.active = active;
        return this;
    }

    public Boolean paid() {
        return paid;
    }
    public Participation paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public Boolean complete() {
        return eventSelections().values().stream().allMatch(EventSelection::isValid);
    }

    public String note() {
        return note != null ? note : "";
    }
    public Participation note(String note) {
        this.note = note;
        return this;
    }

    public Boolean countAsParticipant() {
        Boolean hasParticipantStatus = eventSelections().values().stream()
                .anyMatch(eventSelection -> eventSelection.eventFeature().eventFeatureType().equals(EventFeatureType.PARTICIPANT_STATUS) &&
                        ((ParticipantStatusSelection) eventSelection).eventItem() != null);
        Boolean isShortEvent = event().shortVersion();
        return (hasParticipantStatus || isShortEvent) && active();
    }

    public Person person() {
        return person;
    }
    public Event event() {
        return event;
    }

    @Override
    public Participation self() {
        return this;
    }
}
