package de.scisertec.core.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;
import de.scisertec.core.infrastructure.qualifier.Active;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Entity
public class LocaleString extends DomainModel<LocaleString> {

    @ElementCollection(fetch = FetchType.EAGER)
    Map<Locale, String> internal = new HashMap<>();

    @Transient
    @Inject
    @Active
    Instance<Locale> locale;

    public String value() {
        this.construct();
        return value(locale.get());
    }

    public String value(Locale locale) {
        return internal.get(locale);
    }

    public LocaleString add(Locale locale, String text) {
        internal.put(locale, text);
        return this;
    }

    public Set<Locale> getAvailableLocales() {
        return internal.keySet();
    }

    @Override
    public LocaleString self() {
        return this;
    }
}
