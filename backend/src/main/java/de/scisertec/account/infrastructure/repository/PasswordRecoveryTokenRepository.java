package de.scisertec.account.infrastructure.repository;

import de.scisertec.account.domain.model.PasswordRecoveryToken;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.SingleResultType;

@Repository
public interface PasswordRecoveryTokenRepository extends EntityRepository<PasswordRecoveryToken, Long> {

    @Query(singleResult = SingleResultType.OPTIONAL)
    PasswordRecoveryToken findByValue(String value);

}
