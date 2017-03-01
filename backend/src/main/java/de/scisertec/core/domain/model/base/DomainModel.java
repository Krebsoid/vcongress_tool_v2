package de.scisertec.core.domain.model.base;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;


@MappedSuperclass
public abstract class DomainModel<E> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Transient
    @Inject
    EntityManager entityManager;

    public E construct() {
        BeanProvider.injectFields(self());
        return self();
    }

    @Transactional
    public E save() {
        this.construct();
        entityManager.merge(self());
        return self();
    }

    @Transactional
    public E create() {
        this.construct();
        entityManager.persist(self());
        return self();
    }

    public abstract E self();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
