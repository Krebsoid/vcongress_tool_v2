package de.scisertec.person.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface CountryEditCommand extends Command {

    String getIsoCode();

}
