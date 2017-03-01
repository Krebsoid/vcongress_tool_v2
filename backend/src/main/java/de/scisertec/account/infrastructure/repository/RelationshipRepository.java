package de.scisertec.account.infrastructure.repository;

import de.scisertec.account.domain.model.Relationship;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface RelationshipRepository extends EntityRepository<Relationship, Long> {

}
