package de.scisertec.core.domain.model.base;

public interface Deletable<E> {

    Boolean deleted();
    E deleted(Boolean deleted);

}
