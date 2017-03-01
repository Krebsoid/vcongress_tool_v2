package de.scisertec.person.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface AddressEditCommand extends Command {

    String getStreet();
    String getCity();
    String getZipCode();
    String getState();

    CountryEditCommand getCountry();

}
