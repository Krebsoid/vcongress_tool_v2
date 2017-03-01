package de.scisertec.core.infrastructure.repository;

import de.scisertec.core.domain.model.DataImport;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.SingleResultType;

@Repository
public interface DataImportRepository extends EntityRepository<DataImport, Long> {

    @Query(singleResult = SingleResultType.OPTIONAL)
    DataImport findByImportCls(String importCls);

}
