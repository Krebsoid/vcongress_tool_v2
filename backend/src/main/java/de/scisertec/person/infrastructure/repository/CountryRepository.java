package de.scisertec.person.infrastructure.repository;

import de.scisertec.person.domain.model.Country;
import de.scisertec.person.domain.model.Country_;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class CountryRepository extends AbstractEntityRepository<Country, Long> implements CriteriaSupport<Country> {

    public Country findByISO(String isoCode) {
        return criteria()
                .eq(Country_.isoCode, isoCode)
                .getOptionalResult();
    }

}