package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.DomainEvent;
import de.scisertec.core.domain.model.template.TemplateContent;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.feature.MailFeature;
import de.scisertec.person.domain.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParticipationSave extends DomainEvent<ParticipationSave> implements TemplateContent {

    public Person person;
    public Event event;
    public String method;

    public static ParticipationSave create(Event event, Person person, String method) {
        ParticipationSave participationSave = new ParticipationSave();
        participationSave.event = event;
        participationSave.person = person;
        participationSave.method = method;
        participationSave.fireEvent();
        return participationSave;
    }

    public String method() {
        return method;
    }

    @Override
    public String loggerStamp() {
        return "PARTICIPATION-SAVE - ("+ event.identifier() +"): " + person.firstName() + " " + person.lastName() + " - " + method.toUpperCase();
    }

    @Override
    public ParticipationSave self() {
        return this;
    }

    @Override
    public Map<String, Object> getContent() {
        HashMap<String, Object> content = new HashMap<>();
        content.put("person", person);
        content.put("event", event);
        MailFeature generalMailText = (MailFeature)event.eventFeatures(EventFeatureCategory.MAIL, EventFeatureType.MAIL_GENERAL).stream().findFirst().get();
        MailFeature registrationMailText = (MailFeature)event.eventFeatures(EventFeatureCategory.MAIL, EventFeatureType.MAIL_REGISTRATION).stream().findFirst().get();
        if(registrationMailText.content() != null && !registrationMailText.content().isEmpty()) {
            content.put("dynamicPlain", registrationMailText.content());
            content.put("dynamicHtml", registrationMailText.content().replace("\n", "<br/>"));
        } else if(registrationMailText.applyGeneral()) {
            content.put("dynamicPlain", generalMailText.content());
            content.put("dynamicHtml", generalMailText.content().replace("\n", "<br/>"));
        } else {
            content.put("dynamicPlain", null);
            content.put("dynamicHtml", null);
        }
        return content;
    }
}
