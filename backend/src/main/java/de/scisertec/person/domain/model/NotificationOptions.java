package de.scisertec.person.domain.model;

import de.scisertec.core.domain.model.base.DomainModel;

import javax.persistence.Entity;

@Entity
public class NotificationOptions extends DomainModel<NotificationOptions> {

    Boolean notificationRequest;

    public Boolean notificationRequest() {
        return notificationRequest;
    }
    public NotificationOptions notificationRequest(Boolean notificationRequest) {
        this.notificationRequest = notificationRequest;
        return this;
    }

    @Override
    public NotificationOptions self() {
        return this;
    }
}
