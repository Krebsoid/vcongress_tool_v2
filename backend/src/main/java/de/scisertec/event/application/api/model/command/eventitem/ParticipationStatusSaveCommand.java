package de.scisertec.event.application.api.model.command.eventitem;

import de.scisertec.core.application.api.model.command.Command;

public interface ParticipationStatusSaveCommand extends Command {

    Long getEventItemId();
    Long getEventFeatureId();

}
