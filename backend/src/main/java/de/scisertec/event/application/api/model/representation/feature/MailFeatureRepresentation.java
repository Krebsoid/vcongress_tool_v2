package de.scisertec.event.application.api.model.representation.feature;

import de.scisertec.core.application.api.model.representation.LocaleStringRepresentation;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;

public interface MailFeatureRepresentation extends EventFeatureRepresentation {

    String getContent();
    LocaleStringRepresentation getLocaleContent();
    Boolean getApplyGeneral();

}
