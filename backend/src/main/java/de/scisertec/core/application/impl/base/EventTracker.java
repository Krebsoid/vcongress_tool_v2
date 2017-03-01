package de.scisertec.core.application.impl.base;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.core.domain.model.base.Loggable;
import de.scisertec.core.domain.model.base.TrackedDomainEvent;
import de.scisertec.core.infrastructure.qualifier.Active;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class EventTracker {

    @Inject
    @Active
    Instance<User> user;

    @Inject
    @Active
    Instance<String> group;

    private static final Logger logger = Logger.getLogger(EventTracker.class.getName());

    public void onTrackedDomainEvent(@Observes TrackedDomainEvent<?> trackedDomainEvent) {
        track(trackedDomainEvent);
        log(trackedDomainEvent);
    }

    public void onDomainEvent(@Observes DomainEvent<?> domainEvent) {
        log(domainEvent);
    }

    public synchronized void track(TrackedDomainEvent event) {
        event.save();
    }

    public void log(Loggable event) {
        logger.info(user.get().name()+"(" + group.get() + ") "+event.loggerStamp());
    }

}
