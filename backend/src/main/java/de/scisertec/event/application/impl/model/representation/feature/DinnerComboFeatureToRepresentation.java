package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.event.application.api.model.representation.feature.DinnerComboFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.feature.DinnerFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventParticipationFeatureToRepresentation;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.feature.DinnerComboFeature;

import java.util.List;
import java.util.stream.Collectors;

public class DinnerComboFeatureToRepresentation
        extends EventParticipationFeatureToRepresentation
        implements DinnerComboFeatureRepresentation {

    List<DinnerFeatureRepresentation> dinnerFeatures;

    public DinnerComboFeatureToRepresentation(DinnerComboFeature dinnerFeature) {
        super(dinnerFeature);
        this.dinnerFeatures = dinnerFeature.dinnerFeatures().stream()
                .map(DinnerFeatureToRepresentation::new)
                .collect(Collectors.toList());
    }


    @Override
    public List<DinnerFeatureRepresentation> getDinnerFeatures() {
        return dinnerFeatures;
    }

    @Override
    public void makeParticipationDependant(Participation participation) {
        this.dinnerFeatures.stream().forEach(dinnerFeatureRepresentation -> dinnerFeatureRepresentation.makeParticipationDependant(participation));
    }
}
