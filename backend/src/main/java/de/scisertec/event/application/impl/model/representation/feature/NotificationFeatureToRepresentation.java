package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.event.application.api.model.representation.feature.NotificationFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventFeatureToRepresentation;
import de.scisertec.event.domain.model.feature.NotificationFeature;

public class NotificationFeatureToRepresentation extends EventFeatureToRepresentation implements NotificationFeatureRepresentation {

    String notification;

    public NotificationFeatureToRepresentation(NotificationFeature notificationFeature) {
        super(notificationFeature);
        this.notification = notificationFeature.notification();
    }


    @Override
    public String getNotification() {
        return notification;
    }

}
