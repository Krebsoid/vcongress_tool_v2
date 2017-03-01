package de.scisertec.core.domain.model.base;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.infrastructure.qualifier.Active;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Calendar;
import java.util.GregorianCalendar;


@Entity
@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
public abstract class TrackedDomainEvent<E> extends DomainModel<E> implements Loggable {

    Calendar occurredAt = new GregorianCalendar();

    String activeUser;
    String activeGroup;

    @Inject
    @Transient
    Instance<Event<E>> event;

    @Inject
    @Transient
    @Active
    Instance<User> user;

    @Inject
    @Transient
    @Active
    Instance<String> group;

    public Calendar occurredAt() {
        return occurredAt;
    }
    public String activeUser() {
        return activeUser;
    }
    public String activeGroup() {
        return activeGroup;
    }

    public void fireEvent() {
        BeanProvider.injectFields(self());
        activeUser = user.get().name();
        activeGroup = group.get();
        event.get().fire(self());
    }

    public abstract String loggerStamp();

}
