package de.scisertec.event.domain.model;

import java.util.List;

public interface EventFlagable<E> {

    Boolean checkForEventFlag(String flag);

    List<EventFlag> eventFlags();

    E addEventFlag(String flag);
    E removeEventFlag(Long flagId);
    E removeEventFlag(EventFlag flag);
    E removeEventFlag(String flag);

}
