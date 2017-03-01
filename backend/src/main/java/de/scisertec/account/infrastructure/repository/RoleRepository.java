package de.scisertec.account.infrastructure.repository;

import de.scisertec.account.domain.model.Role;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface RoleRepository extends EntityRepository<Role, Long> {

    Role findByName(String name);

}
