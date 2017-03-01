package de.scisertec.event.infrastructure.startup;

import de.scisertec.customer.infrastructure.startup.CustomerImported;
import de.scisertec.customer.infrastructure.startup.data.CustomerImport;
import de.scisertec.event.infrastructure.startup.data.EventImport;
import de.scisertec.event.infrastructure.startup.data.ParticipantImport;
import de.scisertec.person.infrastructure.startup.PersonsImported;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class StartUpBean {

    @Inject
    EventImport eventImport;

    @Inject
    ParticipantImport participantImport;

    @Inject
    Event<EventImported> eventImportedEvent;

    public void onStartup(@Observes CustomerImported start) {

        eventImport.startImport();
        participantImport.startImport();

    }

}
