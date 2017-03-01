package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.event.application.api.model.representation.feature.EventItemDashboardRepresentation;

public class EventItemToDashboardRepresentation extends AbstractRepresentation implements EventItemDashboardRepresentation {

    String name;
    Integer max;
    Integer actual;

    public EventItemToDashboardRepresentation(String name, Integer max, Integer actual) {
        this.name = name;
        this.max = max;
        this.actual = actual;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getMax() {
        return max;
    }

    @Override
    public Integer getActual() {
        return actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventItemToDashboardRepresentation that = (EventItemToDashboardRepresentation) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
