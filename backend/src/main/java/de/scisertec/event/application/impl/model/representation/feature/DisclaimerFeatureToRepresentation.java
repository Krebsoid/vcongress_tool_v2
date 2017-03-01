package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.event.application.api.model.representation.feature.DisclaimerFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventFeatureToRepresentation;
import de.scisertec.event.domain.model.feature.DisclaimerFeature;

public class DisclaimerFeatureToRepresentation extends EventFeatureToRepresentation implements DisclaimerFeatureRepresentation {

    String content;
    Integer index;
    Boolean mandatory;

    public DisclaimerFeatureToRepresentation(DisclaimerFeature disclaimerFeature) {
        super(disclaimerFeature);
        this.content = disclaimerFeature.content();
        this.index = disclaimerFeature.index();
        this.mandatory = disclaimerFeature.mandatory();
    }


    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Integer getIndex() {
        return index;
    }

    @Override
    public Boolean getMandatory() {
        return mandatory;
    }
}
