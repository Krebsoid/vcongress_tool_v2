package de.scisertec.account.infrastructure.repository;

import de.scisertec.account.domain.model.Group;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.ApplicationScoped;

@Repository
public interface GroupRepository extends EntityRepository<Group, Long> {

    Group findByIdentifier(String identifier);

}
