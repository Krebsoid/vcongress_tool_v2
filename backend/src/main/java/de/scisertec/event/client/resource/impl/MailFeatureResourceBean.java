
package de.scisertec.event.client.resource.impl;

import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.service.MailFeatureService;
import de.scisertec.event.client.model.request.MailFeatureTestRequest;
import de.scisertec.event.client.model.request.MailFeatureUpdateRequest;
import de.scisertec.event.client.resource.api.MailFeatureResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class MailFeatureResourceBean implements MailFeatureResource {

    @Inject
    MailFeatureService mailFeatureService;

    @Override
    public EventFeatureRepresentation updateMailFeature(Long eventFeatureId, @Valid MailFeatureUpdateRequest mailFeatureUpdateRequest) {
        return mailFeatureService.updateMailFeature(eventFeatureId, mailFeatureUpdateRequest);
    }

    @Override
    public void testMailFeature(Long eventFeatureId, @Valid MailFeatureTestRequest mailFeatureTestRequest) {
        mailFeatureService.sendTestMail(eventFeatureId, mailFeatureTestRequest);
    }
}
