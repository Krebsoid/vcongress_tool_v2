package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.feature.MailFeature;

public class MailFeatureEditation extends DomainEvent<MailFeatureEditation> {

    String eventIdentifier;
    String content;
    EventFeature eventFeature;

    public EventFeature eventFeature() {
        return eventFeature;
    }


    public static MailFeatureEditation create(MailFeature eventFeature) {
        MailFeatureEditation mailFeatureEditation = new MailFeatureEditation();
        mailFeatureEditation.content = eventFeature.content();
        mailFeatureEditation.eventIdentifier = eventFeature.event().identifier();
        mailFeatureEditation.eventFeature = eventFeature;
        mailFeatureEditation.fireEvent();
        return mailFeatureEditation;
    }

    @Override
    public String loggerStamp() {
        return "MAIL-FEATURE-EDITATION: " + eventFeature.eventFeatureType().name() + " - " + eventIdentifier;
    }

    @Override
    public MailFeatureEditation self() {
        return this;
    }

}
