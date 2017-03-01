package de.scisertec.event.domain.model.event;

import de.scisertec.core.domain.model.base.TrackedDomainEvent;
import de.scisertec.core.domain.model.template.TemplateContent;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.domain.model.feature.MailFeature;
import de.scisertec.person.domain.model.Person;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PersonEventRegistration extends TrackedDomainEvent<PersonEventRegistration> implements TemplateContent {

    Long eventId;

    @Transient
    public Person person;

    @Transient
    public Event event;

    @Transient
    public Participation participation;

    public static PersonEventRegistration create(Participation participation) {
        PersonEventRegistration registration = new PersonEventRegistration();
        registration.eventId = participation.event().getId();
        registration.event = participation.event();
        registration.person = participation.person();
        registration.participation = participation;
        registration.fireEvent();
        return registration;
    }

    public Long eventId() {
        return eventId;
    }

    @Override
    public String loggerStamp() {
        return "EVENT-REGISTRATION("+ event.identifier() +"): " + person.firstName() + " " + person.lastName() + " | " + person.user().name();
    }

    @Override
    public PersonEventRegistration self() {
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
