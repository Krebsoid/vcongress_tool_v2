package de.scisertec.event.application.impl.service;

import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.event.application.api.model.command.EventLogoUploadCommand;
import de.scisertec.event.application.api.model.representation.EventLogoRepresentation;
import de.scisertec.event.application.api.service.EventLogoService;
import de.scisertec.event.application.impl.model.representation.EventLogoToRepresentation;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;
import de.scisertec.event.infrastructure.repository.EventRepository;
import de.scisertec.event.infrastructure.repository.ParticipantRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class EventLogoServiceBean implements EventLogoService {

    @Inject
    EventRepository eventRepository;

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Inject
    ParticipantRepository participantRepository;

    @Inject
    ConfigurationManager configurationManager;

    @Override
    public EventLogoRepresentation uploadEventLogo(String identifier, EventLogoUploadCommand eventLogoUploadCommand) {
        Event event = eventRepository.findByIdentifier(identifier);
        event.eventLogo(eventLogoUploadCommand.getData());
        eventRepository.save(event);
        return new EventLogoToRepresentation(event.eventLogo());
    }

    @Override
    public void removeEventLogo(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        event.eventLogo().logo(null);
        eventRepository.save(event);
    }
}
