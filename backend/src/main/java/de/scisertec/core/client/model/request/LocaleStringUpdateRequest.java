package de.scisertec.core.client.model.request;

import de.scisertec.core.application.api.model.command.LocaleStringUpdateCommand;

import java.util.HashMap;
import java.util.Locale;

public class LocaleStringUpdateRequest implements LocaleStringUpdateCommand {

    HashMap<Locale, String> localeStrings;

    @Override
    public HashMap<Locale, String> getLocaleStrings() {
        return localeStrings;
    }
}
