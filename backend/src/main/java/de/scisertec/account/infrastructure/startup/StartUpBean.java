package de.scisertec.account.infrastructure.startup;

import de.scisertec.account.infrastructure.startup.data.UserImport;
import de.scisertec.core.infrastructure.startup.ApplicationStarted;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class StartUpBean {

    @Inject
    UserImport personImport;

    @Inject
    Event<UserImported> userImportedEvent;

    public void onStartup(@Observes ApplicationStarted start) {

        personImport.startImport();
        userImportedEvent.fire(new UserImported());

    }

}
