package de.scisertec.event.domain.model.event;

import de.scisertec.event.domain.model.selection.DinnerSelection;

import javax.persistence.Entity;
import javax.persistence.Transient;

public class DinnerSave extends EventSelectionSave {

    DinnerSelection dinnerSelection;

    public static DinnerSave create(DinnerSelection dinnerSelection) {
        DinnerSave dinnerSave = new DinnerSave();
        dinnerSave.eventSelection = dinnerSelection;
        dinnerSave.dinnerSelection = dinnerSelection;
        dinnerSave.fireEvent();
        return dinnerSave;
    }

    @Override
    public String loggerStamp() {
        return super.loggerStamp() + " - " + dinnerSelection.checked();
    }

    @Override
    public DinnerSave self() {
        return this;
    }

}
