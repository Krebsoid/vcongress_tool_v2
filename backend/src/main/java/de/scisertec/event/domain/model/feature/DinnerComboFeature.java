package de.scisertec.event.domain.model.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.DinnerComboFeatureToRepresentation;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.EventSelection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DinnerComboFeature extends EventParticipationFeature {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<DinnerFeature> dinnerFeatures = new HashSet<>();

    public Set<DinnerFeature> dinnerFeatures() {
        return dinnerFeatures;
    }

    @Override
    public EventFeatureType eventFeatureType() {
        return EventFeatureType.DINNER_COMBO;
    }
    @Override
    public EventFeatureCategory eventFeatureCategory() {
        return EventFeatureCategory.PARTICIPATION_WRAPPER;
    }

    @Override
    public EventSelection defaultSelection() {
        return null;
    }

    @Override
    public Boolean hasDefaultSelection() {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public Boolean singleFeature() {
        return Boolean.FALSE;
    }

    @Override
    public EventFeatureRepresentation getRepresentation() {
        return new DinnerComboFeatureToRepresentation(this);
    }
}
