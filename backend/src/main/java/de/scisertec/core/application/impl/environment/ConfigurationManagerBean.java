package de.scisertec.core.application.impl.environment;

import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.application.api.environment.Environment;
import de.scisertec.core.infrastructure.utility.UTF8Control;

import javax.enterprise.context.ApplicationScoped;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@ApplicationScoped
public class ConfigurationManagerBean implements ConfigurationManager {

    private static final String INVALID_KEY="Invalid key '{0}'";
    private static final String MANDATORY_PARAM_MISSING = "No definition found for a mandatory configuration parameter : '{0}'";
    private static final String BUNDLE_FILE_NAME =  "configuration";
    private static final String BUNDLE_PATH = "META-INF/env/";

    @Override
    public Environment getActiveEnvironment() {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PATH + BUNDLE_FILE_NAME);
        String stageString = bundle.getString("stage");
        try {
            return Environment.valueOf(stageString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Environment.DEV;
        }
    }

    @Override
    public Boolean isDevEnvironment() {
        return getActiveEnvironment().equals(Environment.DEV);
    }
    @Override
    public Boolean isStageEnvironment() {
        return getActiveEnvironment().equals(Environment.STAGE);
    }
    @Override
    public Boolean isLiveEnvironment() {
        return getActiveEnvironment().equals(Environment.LIVE);
    }


    @Override
    public String getProperty(String key, Environment environment, String defaultValue) {
        return getProperty(key, environment, defaultValue, false);
    }

    @Override
    public String getProperty(String key, Environment environment) {
        return getProperty(key, environment, "", false);
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, getActiveEnvironment(), "", false);
    }


    @Override
    public String getProperty(String key, Environment environment, String defaultValue, Boolean mandatory) throws IllegalStateException {

        String stage;
        if(environment.equals(Environment.DEPLOYMENT)) {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PATH + BUNDLE_FILE_NAME, new UTF8Control());
            stage = bundle.getString("stage");
        }
        else {
            stage = environment.name().toLowerCase();
        }
        ResourceBundle stageBundle;
        try {
            stageBundle = ResourceBundle.getBundle(BUNDLE_PATH + stage + "_" + BUNDLE_FILE_NAME, new UTF8Control());
        }
        catch(MissingResourceException e) {
            stageBundle = ResourceBundle.getBundle(BUNDLE_PATH + BUNDLE_FILE_NAME, new UTF8Control());
        }


        if (key == null || key.length() == 0) {
            return defaultValue;
        }
        String value;
        try {
            value = stageBundle.getString(key);
            if (value.trim().length() == 0) {
                if (mandatory)
                    throw new IllegalStateException(MessageFormat.format(MANDATORY_PARAM_MISSING, new Object[]{key}));
                else
                    return defaultValue;
            }
            return value;
        } catch (MissingResourceException e) {
            if (mandatory) throw new IllegalStateException(MessageFormat.format(MANDATORY_PARAM_MISSING, new Object[]{key}));
            return null;
        }

    }

}
