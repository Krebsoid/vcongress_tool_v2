package de.scisertec.event.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

import java.util.List;

public interface EventCreationCommand extends Command {

    String getName();
    String getIdentifier();

    String getDescription();
    String getLocation();
    String getStartDate();
    String getEndDate();
    String getStartTime();
    String getEndTime();
    String getRegistrationEnd();

    Integer getLimit();

    String getWebsite();
    String getAdditionalInformation();
    String getContact();

    List<String> getEventModules();

}
