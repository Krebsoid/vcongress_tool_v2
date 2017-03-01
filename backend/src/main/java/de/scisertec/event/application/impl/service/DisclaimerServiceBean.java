package de.scisertec.event.application.impl.service;

import de.scisertec.event.application.api.model.command.feature.DisclaimerUpdateCommand;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.service.DisclaimerService;
import de.scisertec.event.domain.model.feature.DisclaimerFeature;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class DisclaimerServiceBean implements DisclaimerService {

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Override
    public EventFeatureRepresentation updateDisclaimerEventFeature(Long eventFeatureId, DisclaimerUpdateCommand disclaimerUpdateCommand) {
        DisclaimerFeature eventFeature = (DisclaimerFeature) eventFeatureRepository.findBy(eventFeatureId);
        eventFeature.content(disclaimerUpdateCommand.getContent()).mandatory(disclaimerUpdateCommand.getMandatory());
        eventFeatureRepository.save(eventFeature);
        return eventFeature.getRepresentation();
    }
}
