package de.scisertec.core.application.api.model.representation;

import java.util.HashMap;
import java.util.Locale;

public interface LocaleStringRepresentation {
    HashMap<Locale, String> getLocaleStrings();
}
