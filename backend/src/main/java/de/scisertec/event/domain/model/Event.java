package de.scisertec.event.domain.model;

import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.application.impl.helper.CalendarHelper;
import de.scisertec.core.domain.model.base.Deletable;
import de.scisertec.core.domain.model.base.DomainModel;
import de.scisertec.event.domain.model.feature.DeadlineFeature;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.feature.ParticipantStatusFeature;

import javax.inject.Inject;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Event extends DomainModel<Event> implements Deletable<Event> {

    String name;

    String identifier;

    String description;
    String location;
    String website;
    Calendar startDate;
    Calendar endDate;
    String startTime;
    String endTime;

    @Column(length = 1024)
    String contact = "";

    @Column(length = 1024)
    String additionalInformation;

    Boolean published = false;
    @Enumerated
    EventStatus eventStatus = EventStatus.CREATED;

    Boolean deleted = false;
    Boolean shortVersion = false;

    @Transient
    Integer testmodeCapacity = 5;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<EventFeature> eventFeatures = new HashSet<>();

    @ElementCollection
    Set<EventModule> eventModules = new HashSet<>();
    EventLicense eventLicense;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Participation> participants;

    @OneToOne(cascade = CascadeType.ALL)
    EventLogo eventLogo;

    @Transient
    @Inject
    ConfigurationManager configurationManager;

    public EventLogo eventLogo() {
        return eventLogo;
    }
    public Event eventLogo(byte[] logo) {
        if(eventLogo == null) {
            EventLogo eventLogo = new EventLogo();
            eventLogo.event(this).logo(logo);
            this.eventLogo = eventLogo;
        } else {
            eventLogo().logo(logo);
        }
        return this;
    }
    public Event eventLogo(EventLogo eventLogo) {
        this.eventLogo = eventLogo;
        return this;
    }

    public Set<EventModule> eventModules() {
        return eventModules;
    }
    public Event addEventModule(EventModule eventModule) {
        this.eventModules.add(eventModule);
        return this;
    }
    public Event removeEventModule(EventModule eventModule) {
        this.eventModules.remove(eventModule);
        return this;
    }
    public Boolean hasEventModule(EventModule eventModule) {
        return eventModules.stream().anyMatch(e -> e.equals(eventModule));
    }
    public EventLicense eventLicense() {
        return eventLicense;
    }
    public Event eventLicense(EventLicense eventLicense) {
        this.eventLicense = eventLicense;
        return this;
    }

    public Set<EventFeature> eventFeatures() {
        return eventFeatures;
    }
    public Set<EventFeature> eventFeatures(EventFeatureCategory eventFeatureCategory) {
        return eventFeatures.stream().filter(eventFeature -> eventFeature.eventFeatureCategory().equals(eventFeatureCategory)).collect(Collectors.toSet());
    }
    public EventFeature eventFeatures(EventFeatureType eventFeatureType) {
        return eventFeatures.stream().filter(eventFeature -> eventFeature.eventFeatureType().equals(eventFeatureType)).findFirst().orElseGet(() -> null);
    }
    public Set<EventFeature> eventFeatures(EventFeatureCategory eventFeatureCategory, EventFeatureType eventFeatureType) {
        return eventFeatures.stream().filter(eventFeature -> eventFeature.eventFeatureCategory().equals(eventFeatureCategory) && eventFeature.eventFeatureType().equals(eventFeatureType)).collect(Collectors.toSet());
    }
    public EventFeature eventFeature(EventFeatureCategory eventFeatureCategory, EventFeatureType eventFeatureType) {
        return eventFeatures.stream().filter(eventFeature -> eventFeature.eventFeatureCategory().equals(eventFeatureCategory) && eventFeature.eventFeatureType().equals(eventFeatureType)).findFirst().get();
    }
    public Set<Participation> participants() {
        return participants;
    }
    public long validParticipantCount() {
        return participants().stream().filter(Participation::countAsParticipant).count();
    }
    public Boolean isTestmodeCapacityExceeded() {
        return participants().size() >= testmodeCapacity;
    }

    public Boolean isFull() {
        Set<EventFeature> eventFeatures = eventFeatures(EventFeatureCategory.PARTICIPATION, EventFeatureType.PARTICIPANT_STATUS);
        if(!eventFeatures.isEmpty()) {
            ParticipantStatusFeature eventFeature = (ParticipantStatusFeature) eventFeatures.stream().findFirst().get();
            return eventFeature.status().stream().allMatch(eventItem -> !eventItem.isCountValid());
        }
        return false;
    }

    public String name() {
        return name;
    }
    public Event name(String name) {
        this.name = name;
        return this;
    }

    public String additionalInformation() {
        return additionalInformation;
    }
    public Event additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public String identifier() {
        return identifier;
    }
    public Event identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public String description() {
        return description;
    }
    public Event description(String description) {
        this.description = description;
        return this;
    }

    public String location() {
        return location;
    }
    public Event location(String location) {
        this.location = location;
        return this;
    }

    public String website() {
        return website;
    }
    public Event website(String website) {
        this.website = website;
        return this;
    }

    public String contact() {
        return contact;
    }
    public Event contact(String contact) {
        this.contact = contact;
        return this;
    }

    public String startTime() {
        return startTime;
    }
    public Event startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String endTime() {
        return endTime;
    }
    public Event endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public Calendar startDate() {
        return startDate;
    }
    public Event startDate(Calendar startDate) {
        this.startDate = startDate;
        return this;
    }
    public Event startDate(String startDate) {
        this.startDate = CalendarHelper.stringToCalendar(startDate);
        return this;
    }

    public Calendar endDate() {
        return endDate;
    }
    public Event endDate(Calendar endDate) {
        this.endDate = endDate;
        return this;
    }
    public Event endDate(String endDate) {
        this.endDate = CalendarHelper.stringToCalendar(endDate);
        return this;
    }

    public String dateRangeAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if(this.endDate != null && this.endDate.compareTo(this.startDate) != 0) {
            return sdf.format(this.startDate.getTime()) + " - " + sdf.format(this.endDate.getTime());
        } else {
            return sdf.format(this.startDate.getTime());
        }
    }

    public Boolean published() {
        return eventStatus.equals(EventStatus.PUBLISHED);
    }
    public Event published(Boolean published) {
        this.published = published;
        return this;
    }
    public EventStatus eventStatus() {
        return eventStatus;
    }
    public Event eventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
        return this;
    }

    public String registrationLink() {
        this.construct();
        String host;
        if(eventStatus.equals(EventStatus.TESTMODE)) {
            host = configurationManager.getProperty("mail.testhost");
        } else {
            host = configurationManager.getProperty("mail.linkhost");
        }
        return host + "/" + identifier();
    }

    public Boolean isDeadlineExpired(EventFeatureType eventFeatureType) {
        Optional<EventFeature> deadlineFeature = eventFeatures(EventFeatureCategory.DEADLINE).stream()
                .filter(eventFeature -> eventFeature.eventFeatureType().equals(eventFeatureType))
                .findAny();
        return deadlineFeature.isPresent() && ((DeadlineFeature) deadlineFeature.get()).isDeadlineExpired();
    }

    @Override
    public Event self() {
        return this;
    }

    @Override
    public Boolean deleted() {
        return deleted;
    }

    @Override
    public Event deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public Boolean shortVersion() {
        return shortVersion != null ? shortVersion : false;
    }
    public Event shortVersion(Boolean shortVersion) {
        this.shortVersion = shortVersion;
        return this;
    }
}
