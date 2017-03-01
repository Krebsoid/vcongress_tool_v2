package de.scisertec.person.infrastructure.startup;

import de.scisertec.account.infrastructure.startup.UserImported;
import de.scisertec.person.infrastructure.repository.PersonRepository;
import de.scisertec.person.infrastructure.startup.data.CountryImport;
import de.scisertec.person.infrastructure.startup.data.PersonImport;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class StartUpBean {

    @Inject
    PersonImport personImport;
    @Inject
    CountryImport countryImport;

    @Inject
    Event<PersonsImported> personsImportedEvent;

    public void onStartup(@Observes UserImported start) {

        countryImport.startImport();
        personImport.startImport();
        personsImportedEvent.fire(new PersonsImported());

    }

}
