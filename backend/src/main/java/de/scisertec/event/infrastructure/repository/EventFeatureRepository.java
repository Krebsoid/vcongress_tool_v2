package de.scisertec.event.infrastructure.repository;

import de.scisertec.event.domain.model.EventFeature;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

public abstract class EventFeatureRepository extends AbstractEntityRepository<EventFeature, Long> implements CriteriaSupport<EventFeature> {


}
