package de.scisertec.event.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface ShortEventRegistrationCommand extends Command {

    String getGroup();

    String getEmail();

    String getGender();
    String getTitle();

    String getFirstName();
    String getLastName();

    String getCity();
    String getInstitute();

    Boolean getNotificationRequest();

}
