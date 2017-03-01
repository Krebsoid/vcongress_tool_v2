package de.scisertec.event.client.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.scisertec.core.client.model.request.LocaleStringUpdateRequest;
import de.scisertec.core.client.model.request.Request;
import de.scisertec.event.application.api.model.command.feature.MailFeatureUpdateCommand;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MailFeatureUpdateRequest implements MailFeatureUpdateCommand, Request {

    String content;
    Boolean applyGeneral;
    LocaleStringUpdateRequest localeContent;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Boolean getApplyGeneral() {
        return applyGeneral;
    }

    @Override
    public LocaleStringUpdateRequest getLocaleContent() {
        return localeContent;
    }

}
