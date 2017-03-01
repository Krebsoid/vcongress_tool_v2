package de.scisertec.event.application.impl.model.representation.feature;

import de.scisertec.core.application.api.model.representation.LocaleStringRepresentation;
import de.scisertec.core.application.impl.model.representation.LocaleStringToRepresentation;
import de.scisertec.event.application.api.model.representation.feature.MailFeatureRepresentation;
import de.scisertec.event.application.impl.model.representation.EventFeatureToRepresentation;
import de.scisertec.event.domain.model.feature.MailFeature;

public class MailFeatureToRepresentation extends EventFeatureToRepresentation implements MailFeatureRepresentation {

    String content;
    Boolean applyGeneral;
    LocaleStringRepresentation localeContent;

    public MailFeatureToRepresentation(MailFeature mailFeature) {
        super(mailFeature);
        this.content = mailFeature.content();
        this.applyGeneral = mailFeature.applyGeneral();
        this.localeContent = new LocaleStringToRepresentation(mailFeature.localeContent());
    }


    @Override
    public String getContent() {
        return content;
    }

    @Override
    public LocaleStringRepresentation getLocaleContent() {
        return localeContent;
    }

    @Override
    public Boolean getApplyGeneral() {
        return applyGeneral;
    }

    @Override
    public Boolean getPublic() {
        return Boolean.FALSE;
    }

}
