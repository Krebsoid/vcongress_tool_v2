package de.scisertec.event.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

import java.math.BigDecimal;

public interface EventItemCreationCommand extends Command {

    String getName();
    String getDescription();
    Integer getLimit();

    BigDecimal getCost();
    Integer getTax();

}
