package de.scisertec.core.application.impl.model.representation;

import de.scisertec.core.application.api.model.representation.LocaleStringRepresentation;
import de.scisertec.core.domain.model.LocaleString;

import java.util.HashMap;
import java.util.Locale;

public class LocaleStringToRepresentation implements LocaleStringRepresentation {

    HashMap<Locale, String> localeStrings = new HashMap<>();

    public LocaleStringToRepresentation(LocaleString localeString) {
        localeString.getAvailableLocales().stream().forEach(locale -> localeStrings.put(locale, localeString.value(locale)));
    }

    @Override
    public HashMap<Locale, String> getLocaleStrings() {
        return localeStrings;
    }
}
