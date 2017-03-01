package de.scisertec.core.application.api.model.command;

import java.util.HashMap;
import java.util.Locale;

public interface LocaleStringUpdateCommand {
    HashMap<Locale, String> getLocaleStrings();
}
