package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.feature.MailFeature;

public class MailFeatureTest extends DomainEvent<MailFeatureTest> {

    String content;
    EventFeature eventFeature;
    String receiver;

    public EventFeature eventFeature() {
        return eventFeature;
    }


    public static MailFeatureTest create(MailFeature eventFeature, String receiver) {
        MailFeatureTest mailFeatureTest = new MailFeatureTest();
        mailFeatureTest.content = eventFeature.content();
        mailFeatureTest.eventFeature = eventFeature;
        mailFeatureTest.receiver = receiver;
        mailFeatureTest.fireEvent();
        return mailFeatureTest;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public String loggerStamp() {
        return "MAIL-FEATURE-TEST: " + eventFeature.eventFeatureType().name() + " - " + eventFeature.event().identifier()+ " - to: " + receiver;
    }

    @Override
    public MailFeatureTest self() {
        return this;
    }

}
