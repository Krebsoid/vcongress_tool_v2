package de.scisertec.core.infrastructure.startup;

import de.scisertec.core.application.api.environment.ConfigurationManager;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.security.auth.login.Configuration;

@Singleton
@Startup
public class StartUpBean {

    private static final Logger logger = Logger.getLogger(StartUpBean.class.getName());

    @Inject
    Event<ApplicationStarted> applicationStartedEvent;

    @Inject
    ConfigurationManager configurationManager;

    @PostConstruct
    public void onStartUp() {

        logger.info("Base Webapp Prototype started");
        logger.info("Import Start");
        logger.info("With configuration " + configurationManager.getActiveEnvironment().name());
        applicationStartedEvent.fire(new ApplicationStarted());
        logger.info("Import End");

    }

}
