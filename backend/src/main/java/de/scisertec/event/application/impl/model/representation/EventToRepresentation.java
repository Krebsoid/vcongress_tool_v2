package de.scisertec.event.application.impl.model.representation;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventLogoRepresentation;
import de.scisertec.event.application.api.model.representation.EventRepresentation;
import de.scisertec.event.domain.model.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class EventToRepresentation extends AbstractRepresentation implements EventRepresentation {

    Long id;
    String name;
    String identifier;
    Boolean published;
    Boolean shortVersion;
    String link;

    String startDate;
    String endDate;
    String startTime;
    String endTime;
    String description;
    String location;
    String website;
    String additionalInformation;

    String contact;

    String eventStatus;

    List<String> eventModules;
    String eventLicense;

    List<EventFeatureRepresentation> eventFeatures;

    EventLogoRepresentation eventLogo;

    User user;

    public EventToRepresentation(Event event) {
        setEventFields(event);
    }

    public EventToRepresentation(Event event, User user) {
        setUser(user);
        setEventFields(event);
        filterFeatures(event);
    }

    private void setEventFields(Event event) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.name = event.name();
        this.identifier = event.identifier();
        this.id = event.getId();
        this.published = event.published();
        this.shortVersion = event.shortVersion();
        this.link = event.registrationLink();
        this.startDate = event.startDate() != null ? sdf.format(event.startDate().getTime()) : "";
        this.endDate = event.endDate() != null ? sdf.format(event.endDate().getTime()) : "";
        this.startTime = event.startTime() != null ? event.startTime() : "";
        this.endTime = event.endTime() != null ? event.endTime() : "";
        this.location = event.location();
        this.description = event.description();
        this.website = event.website();
        this.additionalInformation = event.additionalInformation();
        this.contact = event.contact();
        this.eventStatus = event.eventStatus().name();
        this.eventModules = event.eventModules().stream().map(Enum::name).collect(Collectors.toList());
        this.eventLicense = event.eventLicense() != null ? event.eventLicense().name() : "";
        this.eventFeatures = event.eventFeatures().stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .map(eventFeature -> {
                    EventFeatureRepresentation representation = eventFeature.getRepresentation();
                    if(this.user != null) {
                        representation.setUser(this.user);
                    }
                    return representation;
                }).collect(Collectors.toList());
        this.eventLogo = new EventLogoToRepresentation(event.eventLogo());
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
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getEventStatus() {
        return eventStatus;
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
    public String getContact() {
        return contact;
    }

    @Override
    public List<String> getEventModules() {
        return eventModules;
    }

    @Override
    public String getEventLicense() {
        return eventLicense;
    }

    @Override
    public List<EventFeatureRepresentation> getEventFeatures() {
        return eventFeatures;
    }

    @Override
    public EventLogoRepresentation getEventLogo() {
        return eventLogo;
    }

    @Override
    public void makeParticipationDependant(Participation participation) {
        this.eventFeatures.stream().forEach(eventFeatureRepresentation -> eventFeatureRepresentation.makeParticipationDependant(participation));
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    private void filterFeatures(Event event) {
        if((!this.user.hasRole("system", "ADMIN") &&
                !this.user.hasRole(event.identifier(), "ORGANISATOR"))) {
            this.eventFeatures = this.eventFeatures.stream().filter(EventFeatureRepresentation::getPublic).collect(Collectors.toList());
        }
    }
}
