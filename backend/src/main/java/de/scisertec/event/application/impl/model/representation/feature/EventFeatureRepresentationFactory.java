package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventFeatureToRepresentation;
import de.scisertec.event.application.impl.model.representation.EventParticipationFeatureToRepresentation;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.feature.*;
import de.scisertec.payment.application.impl.model.representation.feature.CancellationFeatureToRepresentation;
import de.scisertec.payment.application.impl.model.representation.feature.PaymentFeatureToRepresentation;
import de.scisertec.payment.domain.model.feature.CancellationFeature;
import de.scisertec.payment.domain.model.feature.PaymentFeature;

public class EventFeatureRepresentationFactory {

    public static EventFeatureRepresentation toRepresentation(EventFeature eventFeature) {
        return eventFeature.getRepresentation();
    }

}
