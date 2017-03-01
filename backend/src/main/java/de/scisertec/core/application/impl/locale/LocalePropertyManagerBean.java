package de.scisertec.core.application.impl.locale;

import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.core.infrastructure.qualifier.Active;
import de.scisertec.core.infrastructure.utility.UTF8Control;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@RequestScoped
public class LocalePropertyManagerBean implements LocalePropertyManager {
    private static final String INVALID_KEY="Invalid key '{0}'";
    private static final String BUNDLE_FILE_NAME =  "localization";
    private static final String BUNDLE_PATH = "META-INF/locale/";

    private static final String DEFAULT_LOCALE = "de";

    private String bundlePath;

    @Inject
    @Active
    Instance<Locale> locale;

    @Override
    public String getLocalizedProperty(String key) {
        return getLocalizedProperty(bundlePath, key, locale.get().getLanguage());
    }

    @Override
    public String getLocalizedProperty(String specifiedBundlePath, String key) {
        return getLocalizedProperty(specifiedBundlePath, key, locale.get().getLanguage());
    }

    @Override
    public String getLocalizedProperty(String specifiedBundlePath, String key, String locale) throws IllegalStateException {
          
        ResourceBundle localeBundle;
        try {
            localeBundle = ResourceBundle.getBundle(BUNDLE_PATH + specifiedBundlePath + "/" + locale + "_" + BUNDLE_FILE_NAME, new UTF8Control());
        } catch (Exception e) {
            localeBundle = ResourceBundle.getBundle(BUNDLE_PATH + specifiedBundlePath + "/" + DEFAULT_LOCALE + "_" + BUNDLE_FILE_NAME, new UTF8Control());
        }

        String value;
        try {
            value = localeBundle.getString(key);
            return value;
        } catch (MissingResourceException e) {
            return MessageFormat.format(INVALID_KEY, new String[]{key}[0]);
        }
    }

    public void setBundlePath(String localePropertyPackage) {
        this.bundlePath = localePropertyPackage;
    }
}
