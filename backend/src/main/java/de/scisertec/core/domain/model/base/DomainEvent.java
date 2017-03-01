package de.scisertec.core.domain.model.base;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.Transient;

public abstract class DomainEvent<E> implements Loggable {

    @Inject
    @Transient
    Instance<Event<E>> event;

    public void fireEvent() {
        BeanProvider.injectFields(self());
        event.get().fire(self());
    }

    public abstract String loggerStamp();

    public abstract E self();
}
