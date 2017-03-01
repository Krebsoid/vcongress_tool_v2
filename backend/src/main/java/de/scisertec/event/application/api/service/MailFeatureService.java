package de.scisertec.event.application.api.service;

import de.scisertec.core.application.api.model.service.Service;
import de.scisertec.event.application.api.model.command.feature.DisclaimerUpdateCommand;
import de.scisertec.event.application.api.model.command.feature.MailFeatureTestCommand;
import de.scisertec.event.application.api.model.command.feature.MailFeatureUpdateCommand;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;

public interface MailFeatureService extends Service {

    EventFeatureRepresentation updateMailFeature(Long eventFeatureId, MailFeatureUpdateCommand mailFeatureUpdateCommand);
    void sendTestMail(Long eventFeatureId, MailFeatureTestCommand mailFeatureTestCommand);

}
