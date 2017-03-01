package de.scisertec.event.application.impl.model.representation.selection;

import de.scisertec.event.application.api.model.representation.selection.DinnerSelectionRepresentation;
import de.scisertec.event.application.impl.model.representation.EventSelectionToRepresentation;
import de.scisertec.event.domain.model.selection.DinnerSelection;

public class DinnerSelectionToRepresentation extends EventSelectionToRepresentation implements DinnerSelectionRepresentation {

    Boolean checked;

    public DinnerSelectionToRepresentation(DinnerSelection dinnerSelection) {
        super(dinnerSelection);
        this.checked = dinnerSelection.checked();
    }

    @Override
    public Boolean getChecked() {
        return checked;
    }
}
