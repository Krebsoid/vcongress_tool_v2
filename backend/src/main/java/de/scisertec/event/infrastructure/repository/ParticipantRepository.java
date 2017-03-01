package de.scisertec.event.infrastructure.repository;

import de.scisertec.account.domain.model.User;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.Participation_;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.domain.model.Person_;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import java.util.List;

public abstract class ParticipantRepository extends AbstractEntityRepository<Participation, Long> implements CriteriaSupport<Participation> {

    public List<Participation> findAllByEvent(Event event) {
        return criteria().eq(Participation_.event, event).getResultList();
    }

    public Participation findByUser(User user) {
        return criteria()
                .join(Participation_.person, where(Person.class).eq(Person_.user, user))
                .getOptionalResult();
    }

}
