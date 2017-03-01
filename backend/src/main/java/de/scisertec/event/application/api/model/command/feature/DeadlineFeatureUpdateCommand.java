package de.scisertec.event.application.api.model.command.feature;

import de.scisertec.core.application.api.model.command.Command;

public interface DeadlineFeatureUpdateCommand extends Command {

    String getDeadline();

}
