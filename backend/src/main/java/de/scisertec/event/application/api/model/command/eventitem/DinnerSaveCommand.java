package de.scisertec.event.application.api.model.command.eventitem;

import de.scisertec.core.application.api.model.command.Command;

public interface DinnerSaveCommand extends Command {

    Boolean getChecked();
    Long getEventFeatureId();

}
