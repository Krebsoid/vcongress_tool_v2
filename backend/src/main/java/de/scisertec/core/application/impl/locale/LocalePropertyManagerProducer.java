package de.scisertec.core.application.impl.locale;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

public class LocalePropertyManagerProducer {

    @Inject
    LocalePropertyManagerBean localePropertyManager;

    @Produces
    @BundlePath
    public LocalePropertyManager getManager(InjectionPoint injectionPoint) {
        BundlePath annotation = injectionPoint.getAnnotated().getAnnotation(BundlePath.class);
        if(annotation != null) {
            localePropertyManager.setBundlePath(annotation.value());
        }
        return localePropertyManager;
    }

}
