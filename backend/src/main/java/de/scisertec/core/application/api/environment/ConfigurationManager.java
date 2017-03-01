package de.scisertec.core.application.api.environment;

public interface ConfigurationManager {
    Environment getActiveEnvironment();

    Boolean isDevEnvironment();

    Boolean isStageEnvironment();

    Boolean isLiveEnvironment();

    String getProperty(String key, Environment environment, String defaultValue);

    String getProperty(String key, Environment environment);

    String getProperty(String key);

    String getProperty(String key, Environment environment, String defaultValue, Boolean mandatory) throws IllegalStateException;
}
