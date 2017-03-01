package de.scisertec.person.infrastructure.repository;

import de.scisertec.account.domain.model.*;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.domain.model.Person_;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import java.util.List;

@Repository
public abstract class PersonRepository extends AbstractEntityRepository<Person, Long> implements CriteriaSupport<Person> {

    public List<Person> findByGroup(Group group) {
        return criteria()
                .join(Person_.user, where(User.class).eq(User_.deleted, false)
                    .join(User_.relationships,
                            where(Relationship.class).eq(Relationship_.group, group)))
                .getResultList();
    }

    public List<Person> findAll() {
        return criteria()
                .join(Person_.user, where(User.class).eq(User_.deleted, false))
                .getResultList();
    }

    public Person findByUser(User user) {
        return criteria()
                .eq(Person_.user, user)
                .getOptionalResult();
    }

}