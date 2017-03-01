package de.scisertec.account.domain.factory;

import de.scisertec.account.domain.model.Group;
import de.scisertec.core.domain.factory.Factory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GroupFactory implements Factory {

    public Group create() {
        return new Group();
    }

}
