package de.scisertec.event.infrastructure.repository;

import de.scisertec.core.infrastructure.exception.NoResourceFoundException;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.EventStatus;
import de.scisertec.event.domain.model.Event_;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import java.util.List;

public abstract class EventRepository extends AbstractEntityRepository<Event, Long> implements CriteriaSupport<Event> {


    public Event findByIdentifier(String identifier) {
        Event event = criteria()
                .eq(Event_.identifier, identifier)
                .getOptionalResult();
        if(event == null) {
            throw new NoResourceFoundException(Event.class, identifier);
        }
        return event;
    }

    public List<Event> findAllArchived() {
        return criteria()
                .eq(Event_.eventStatus, EventStatus.ARCHIVED)
                .getResultList();
    }

    public List<Event> findAllActive() {
        return criteria()
                .notEq(Event_.eventStatus, EventStatus.ARCHIVED)
                .getResultList();
    }

}
