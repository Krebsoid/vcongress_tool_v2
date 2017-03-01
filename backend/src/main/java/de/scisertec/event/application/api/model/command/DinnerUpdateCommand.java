package de.scisertec.event.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface DinnerUpdateCommand extends Command {

    String getName();
    Integer getLimit();

}
