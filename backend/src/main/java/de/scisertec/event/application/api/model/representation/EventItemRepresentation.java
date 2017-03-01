package de.scisertec.event.application.api.model.representation;

import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.event.domain.model.Participation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EventItemRepresentation extends Representation {

    Long getId();
    String getName();
    String getDescription();

    Boolean getAvailableValid();
    Boolean getDateValid();
    Boolean getCountValid();

    String getStartDateEarlyBird();
    String getEndDateEarlyBird();

    String getStartDate();
    String getEndDate();
    Integer getLimit();

    BigDecimal getCost();
    Integer getTax();

    List<EventFlagRepresentation> getEventFlags();

    void makeParticipationDependant(Participation participation);

}
