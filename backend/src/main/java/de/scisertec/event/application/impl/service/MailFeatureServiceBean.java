package de.scisertec.event.application.impl.service;

import de.scisertec.event.application.api.model.command.feature.MailFeatureTestCommand;
import de.scisertec.event.application.api.model.command.feature.MailFeatureUpdateCommand;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.service.MailFeatureService;
import de.scisertec.event.domain.model.event.MailFeatureEditation;
import de.scisertec.event.domain.model.event.MailFeatureTest;
import de.scisertec.event.domain.model.feature.MailFeature;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class MailFeatureServiceBean implements MailFeatureService {

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Override
    public EventFeatureRepresentation updateMailFeature(Long eventFeatureId, MailFeatureUpdateCommand mailFeatureUpdateCommand) {
        MailFeature eventFeature = (MailFeature) eventFeatureRepository.findBy(eventFeatureId);
        eventFeature.applyGeneral(mailFeatureUpdateCommand.getApplyGeneral());
        eventFeature.content(mailFeatureUpdateCommand.getContent());
        eventFeature.localeContent(mailFeatureUpdateCommand.getLocaleContent().getLocaleStrings());
        eventFeatureRepository.save(eventFeature);
        MailFeatureEditation.create(eventFeature);
        return eventFeature.getRepresentation();
    }

    @Override
    public void sendTestMail(Long eventFeatureId, MailFeatureTestCommand mailFeatureTestCommand) {
        MailFeature eventFeature = (MailFeature) eventFeatureRepository.findBy(eventFeatureId);
        MailFeatureTest.create(eventFeature, mailFeatureTestCommand.getReceiver());
    }


}
