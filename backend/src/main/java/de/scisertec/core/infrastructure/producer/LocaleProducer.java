package de.scisertec.core.infrastructure.producer;

import de.scisertec.core.infrastructure.qualifier.Active;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import java.util.Locale;

@ApplicationScoped
public class LocaleProducer {

    Locale locale = Locale.UK;

    @Produces
    @Active
    public Locale getActiveLocale() {
        return locale;
    }

    public void onLocaleChange(@Observes @Active Locale changedLocale) {
        locale = changedLocale;
    }

}

