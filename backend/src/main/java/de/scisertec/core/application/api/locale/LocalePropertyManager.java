package de.scisertec.core.application.api.locale;

public interface LocalePropertyManager {
    String getLocalizedProperty(String key);

    String getLocalizedProperty(String specifiedBundlePath, String key);

    String getLocalizedProperty(String specifiedBundlePath, String key, String locale) throws IllegalStateException;
}
