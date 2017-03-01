package de.scisertec.event.application.impl.model.representation;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventFlagRepresentation;
import de.scisertec.event.application.api.model.representation.EventItemRepresentation;
import de.scisertec.event.domain.model.EventItem;
import de.scisertec.event.domain.model.Participation;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class EventItemToRepresentation extends AbstractRepresentation implements EventItemRepresentation {

    Long id;
    String name;
    String description;

    String startDate;
    String endDate;
    Integer limit;

    String startDateEarlyBird;
    String endDateEarlyBird;

    BigDecimal cost;
    Integer tax;

    Boolean availableValid;
    Boolean dateValid;
    Boolean countValid;

    List<EventFlagRepresentation> eventFlags;

    EventItem eventItem;

    public EventItemToRepresentation(EventItem eventItem) {
        this.eventItem = eventItem;
        this.name = eventItem.name();
        this.description = eventItem.description();
        this.id = eventItem.getId();
        this.startDate = eventItem.startDate() != null ? eventItem.startDate().toString() : "";
        this.endDate = eventItem.endDate() != null ? eventItem.endDate().toString() : "";
        this.limit = eventItem.participantLimit();
        if(eventItem.checkForEventFlag("EARLY_BIRD") || (eventItem.findTwin() != null && eventItem.findTwin().checkForEventFlag("EARLY_BIRD"))) {
            if(eventItem.endDate() == null) {
                this.startDateEarlyBird = eventItem.findTwin().startDate().toString();
                this.endDateEarlyBird = eventItem.findTwin().endDate().toString();
            } else {
                this.startDateEarlyBird = eventItem.startDate().toString();
                this.endDateEarlyBird = eventItem.endDate().toString();
            }
        }
        this.cost = eventItem.cost();
        this.tax = eventItem.tax();
        this.availableValid = true;
        this.countValid = true;
        this.dateValid = true;
        this.eventFlags = eventItem.eventFlags().stream().map(EventFlagToRepresentation::new).collect(Collectors.toList());
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
    public String getDescription() {
        return description;
    }

    @Override
    public Boolean getAvailableValid() {
        return availableValid;
    }

    @Override
    public Boolean getDateValid() {
        return dateValid;
    }

    @Override
    public Boolean getCountValid() {
        return countValid;
    }

    @Override
    public String getStartDateEarlyBird() {
        return startDateEarlyBird;
    }

    @Override
    public String getEndDateEarlyBird() {
        return endDateEarlyBird;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public Integer getTax() {
        return tax;
    }

    public void makeParticipationDependant(Participation participation) {
        this.availableValid = eventItem.isAvailableValid();
        this.countValid = eventItem.isCountValid();
        this.dateValid = eventItem.isDateValid(participation);
    }

    @Override
    public List<EventFlagRepresentation> getEventFlags() {
        return eventFlags;
    }
}
