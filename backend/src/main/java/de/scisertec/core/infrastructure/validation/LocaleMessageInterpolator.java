package de.scisertec.core.infrastructure.validation;


import de.scisertec.core.infrastructure.qualifier.Active;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.Configuration;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import java.util.Locale;

public class LocaleMessageInterpolator implements MessageInterpolator {

    @Inject
    @Active
    Instance<Locale> activeLocale;

    public String interpolate(String messageTemplate, Context context) {
        Configuration<?> configuration = Validation.byDefaultProvider().configure();
        MessageInterpolator defaultMessageInterpolator = configuration.getDefaultMessageInterpolator();
        return defaultMessageInterpolator.interpolate(messageTemplate, context, activeLocale.get());
    }

    public String interpolate(String messageTemplate, Context context, Locale locale) {
        Configuration<?> configuration = Validation.byDefaultProvider().configure();
        MessageInterpolator defaultMessageInterpolator = configuration.getDefaultMessageInterpolator();
        return defaultMessageInterpolator.interpolate(messageTemplate, context, activeLocale.get());
    }
}
