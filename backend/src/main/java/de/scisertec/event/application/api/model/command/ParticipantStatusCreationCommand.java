package de.scisertec.event.application.api.model.command;

public interface ParticipantStatusCreationCommand extends EventItemCreationCommand {

    String getStartDate();
    String getEndDate();
    Boolean getEarlyBird();
    Boolean getEarlyBirdActive();

}
