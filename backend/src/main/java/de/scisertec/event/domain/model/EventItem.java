package de.scisertec.event.domain.model;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.core.application.impl.helper.CalendarHelper;
import de.scisertec.core.domain.model.base.DomainModel;
import de.scisertec.event.domain.model.feature.ParticipantStatusFeature;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Entity
public class EventItem extends DomainModel<EventItem> implements EventFlagable<EventItem> {

    String name;

    String description;

    Calendar startDate;
    Calendar endDate;

    BigDecimal cost;
    Integer tax;

    Integer participantLimit = 0;
    Boolean closed = false;

    @ManyToOne
    EventFeature eventFeature;

    @OneToMany(cascade = CascadeType.ALL)
    List<EventFlag> eventFlags = new ArrayList<>();

    EventItemType eventItemType;

    @Inject
    @Transient
    @BundlePath("general")
    LocalePropertyManager localePropertyManager;


    public EventItem eventFeature(EventFeature eventFeature) {
        this.eventFeature = eventFeature;
        return this;
    }
    public EventFeature eventFeature() {
        return eventFeature;
    }

    public EventItem eventItemType(EventItemType eventItemType) {
        this.eventItemType = eventItemType;
        return this;
    }
    public EventItemType eventItemType() {
        return eventItemType;
    }

    public EventItem name(String name) {
        this.name = name;
        return this;
    }
    public String name() {
        return name;
    }
    public String nameWithPostfix() {
        if(checkForEventFlag("EARLY_BIRD")) {
            this.construct();
            return name + " (" + localePropertyManager.getLocalizedProperty("app.general.event.early_bird") + ")";
        } else {
            return name;
        }
    }


    public EventItem description(String description) {
        this.description = description;
        return this;
    }
    public String description() {
        return description;
    }

    public EventItem startDate(Calendar startDate) {
        this.startDate = startDate;
        return this;
    }
    public EventItem startDate(String startDate) {
        this.startDate = CalendarHelper.stringToCalendar(startDate);
        return this;
    }
    public LocalDate startDate() {
        return startDate != null ? LocalDate.fromCalendarFields(startDate) : null;
    }

    public EventItem endDate(Calendar endDate) {
        this.endDate = endDate;
        return this;
    }
    public EventItem endDate(String endDate) {
        this.endDate = CalendarHelper.stringToCalendar(endDate);
        return this;
    }
    public EventItem noStartDate() {
        this.startDate = null;
        return this;
    }
    public EventItem noEndDate() {
        this.endDate = null;
        return this;
    }
    public LocalDate endDate() {
        return endDate != null ? LocalDate.fromCalendarFields(endDate) : null;
    }

    public EventItem participantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
        return this;
    }
    public Integer participantLimit() {
        return participantLimit;
    }

    public Boolean closed() {
        return closed;
    }
    public EventItem closed(Boolean closed) {
        this.closed = closed;
        return this;
    }

    public EventItem cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }
    public BigDecimal cost() {
        return cost != null ? cost : new BigDecimal(0);
    }

    public EventItem tax(Integer tax) {
        this.tax = tax;
        return this;
    }
    public Integer tax() {
        return tax;
    }

    public EventItem addEventFlag(String flag) {
        if(!checkForEventFlag(flag)) {
            EventFlag eventFlag = new EventFlag();
            eventFlag.name = flag;
            this.eventFlags.add(eventFlag);
        }
        return this;
    }
    public EventItem removeEventFlag(EventFlag eventFlag) {
        this.eventFlags.remove(eventFlag);
        return this;
    }
    public EventItem removeEventFlag(Long flagId) {
        this.eventFlags.removeIf(eventFlag -> eventFlag.getId().equals(flagId));
        return this;
    }
    public EventItem removeEventFlag(String flag) {
        this.eventFlags.removeIf(eventFlag -> eventFlag.name.equals(flag));
        return this;
    }

    @Override
    public Boolean checkForEventFlag(String flag) {
        return eventFlags().stream().anyMatch(eventFlag -> eventFlag.name.equals(flag));
    }

    public Boolean isAvailable(Participation participation) {
        return isCountValid() && isDateValid(participation) && isAvailableValid();
    }

    public Boolean isAvailableValid() {
        return !closed;
    }

    public Boolean isCountValid() {
        Long count = this.eventFeature().event().participants().stream()
                .filter(Participation::active)
                .filter(p -> p.hasEventSelection(this))
                .count();
        if(findTwin() != null) {
            count += this.eventFeature().event().participants().stream()
                    .filter(Participation::active)
                    .filter(p -> p.hasEventSelection(findTwin())
            ).count();
        }
        if(this.participantLimit == null || this.participantLimit == 0) {
            return true;
        } else {
            return count < this.participantLimit;
        }
    }

    public EventItem findTwin() {
        try {
            Optional<EventItem> item = ((ParticipantStatusFeature) this.eventFeature()).status().stream()
                    .filter(eventItem -> eventItem.name.equals(this.name) && !eventItem.getId().equals(this.getId()))
                    .findFirst();
            if(item.isPresent()) {
                return item.get();
            } else {
                return null;
            }
        } catch (ClassCastException c) {
            return null;
        }
    }

    public Boolean isDateValid(Participation participation) {
        if(this.startDate != null && this.endDate == null) {
            return LocalDate.fromCalendarFields(this.startDate).minusDays(1).isBefore(LocalDate.fromCalendarFields(participation.dateOfRegistration));
        }
        else if(this.startDate != null && this.endDate != null) {
            return LocalDate.fromCalendarFields(this.endDate).plusDays(1).isAfter(LocalDate.fromCalendarFields(participation.dateOfRegistration)) &&
                    LocalDate.fromCalendarFields(this.startDate).minusDays(1).isBefore(LocalDate.fromCalendarFields(participation.dateOfRegistration));
        } else {
            return true;
        }
    }

    public List<EventFlag> eventFlags() {
        return eventFlags;
    }

    @Override
    public EventItem self() {
        return this;
    }
}
