package de.scisertec.account.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;

import java.util.List;

public interface GroupRepresentation extends Representation {

    String getName();
    List<String> getAvailableRoles();

}
