package de.scisertec.core.application.api.environment;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.inject.Inject;

public abstract class StageWrapper {

    @Inject
    ConfigurationManager configurationManager;

    protected StageWrapper() {
        BeanProvider.injectFields(this);
    }

    protected abstract String propertyKey();
    protected abstract String originalValue();

    public String getValue() {
        return !configurationManager.isDevEnvironment() ? originalValue() : configurationManager.getProperty(propertyKey());
    }

}
