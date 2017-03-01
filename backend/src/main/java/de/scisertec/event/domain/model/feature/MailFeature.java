package de.scisertec.event.domain.model.feature;

import de.scisertec.core.domain.model.LocaleString;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.feature.MailFeatureToRepresentation;
import de.scisertec.event.domain.model.EventFeature;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;

@Entity
public class MailFeature extends EventFeature {

    @Column(length = 2048)
    String content;

    @OneToOne(cascade = CascadeType.ALL)
    LocaleString localeContent = new LocaleString();

    @Enumerated
    EventFeatureType eventFeatureType;

    Boolean applyGeneral = Boolean.FALSE;

    @Override
    public EventFeatureType eventFeatureType() {
        return eventFeatureType;
    }

    public MailFeature eventFeatureType(EventFeatureType eventFeatureType) {
        this.eventFeatureType = eventFeatureType;
        return this;
    }

    public String content() {
        return content;
    }
    public MailFeature content(String content) {
        this.content = content;
        return this;
    }

    public LocaleString localeContent() {
        return localeContent;
    }
    public MailFeature localeContent(HashMap<Locale, String> localeContent) {
        localeContent.forEach((locale, s) -> this.localeContent.add(locale, s));
        return this;
    }

    public Boolean applyGeneral() {
        return applyGeneral != null ? applyGeneral : Boolean.FALSE;
    }
    public MailFeature applyGeneral(Boolean applyGeneral) {
        this.applyGeneral = applyGeneral;
        return this;
    }


    public EventFeatureCategory eventFeatureCategory() {
        return EventFeatureCategory.MAIL;
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
        return new MailFeatureToRepresentation(this);
    }

    @Override
    public MailFeature self() {
        return this;
    }
}
