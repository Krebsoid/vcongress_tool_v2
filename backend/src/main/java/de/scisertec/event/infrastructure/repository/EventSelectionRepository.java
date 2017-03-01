package de.scisertec.event.infrastructure.repository;

import de.scisertec.event.domain.model.EventSelection;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

public abstract class EventSelectionRepository extends AbstractEntityRepository<EventSelection, Long> implements CriteriaSupport<EventSelection> {


}
