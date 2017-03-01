package de.scisertec.event.client.model.request;

import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.EventCreationCommand;
import de.scisertec.event.infrastructure.validation.EventUniqueIdentifier;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@EventUniqueIdentifier(identifier = "identifier")
public class EventCreationRequest implements EventCreationCommand, Request {

    @NotEmpty
    String name;
    @NotEmpty
    String identifier;

    String description;
    String location;
    @URL
    String website;
    String additionalInformation;
    @NotEmpty
    String startDate;
    String endDate;
    String startTime;
    String endTime;
    String registrationEnd;
    String contact;

    Integer limit;

    List<String> eventModules;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getLocation() {
        return location;
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
    public String getRegistrationEnd() {
        return registrationEnd;
    }

    @Override
    public Integer getLimit() {
        return limit;
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
    public String getContact() {
        return contact;
    }

    @Override
    public List<String> getEventModules() {
        return eventModules;
    }
}
