package de.scisertec.core.infrastructure.interceptor;


import de.scisertec.core.infrastructure.qualifier.Active;
import org.apache.commons.lang.LocaleUtils;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Locale;

@Provider
public class LanguageInterceptor implements ContainerRequestFilter {

    @Inject
    @Active
    Event<Locale> localeEvent;

    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        try {
            if(containerRequestContext.getCookies().containsKey("language")) {
                Locale locale = LocaleUtils.toLocale(containerRequestContext.getCookies().get("language").getValue());
                localeEvent.fire(locale);
            }
        }
        catch(Exception e) {
            localeEvent.fire(Locale.GERMANY);
        }
    }
}
