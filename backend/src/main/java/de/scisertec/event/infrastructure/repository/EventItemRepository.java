package de.scisertec.event.infrastructure.repository;

import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.EventItem;
import de.scisertec.event.domain.model.EventItem_;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import java.util.List;

public abstract class EventItemRepository extends AbstractEntityRepository<EventItem, Long> implements CriteriaSupport<EventItem> {

    public List<EventItem> findByEventFeature(EventFeature event) {
        return criteria()
                .eq(EventItem_.eventFeature, event)
                .getResultList();
    }

}
