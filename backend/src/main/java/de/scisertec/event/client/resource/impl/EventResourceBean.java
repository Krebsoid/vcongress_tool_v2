
package de.scisertec.event.client.resource.impl;

import de.scisertec.core.infrastructure.qualifier.Logging;
import de.scisertec.event.application.api.model.representation.EventDashboardRepresentation;
import de.scisertec.event.application.api.model.representation.EventFeatureRepresentation;
import de.scisertec.event.application.api.model.representation.EventRepresentation;
import de.scisertec.event.application.api.service.EventService;
import de.scisertec.event.client.model.request.*;
import de.scisertec.event.client.resource.api.EventResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class EventResourceBean implements EventResource {

    @Inject
    EventService eventService;

    @Logging
    public EventRepresentation create(@Valid EventCreationRequest eventCreationRequest) {
        return eventService.createEvent(eventCreationRequest);
    }

    @Override
    public EventRepresentation createShort(@Valid EventCreationRequest eventCreationRequest) {
        return eventService.createShortEvent(eventCreationRequest);
    }

    @Logging
    public EventRepresentation edit(String identifier, @Valid EventUpdateRequest eventCreationRequest) {
        return eventService.editEvent(identifier, eventCreationRequest);
    }

    @Logging
    public EventRepresentation editShort(String identifier, @Valid EventUpdateRequest eventCreationRequest) {
        return eventService.editEvent(identifier, eventCreationRequest);
    }

    @Logging
    public Response delete(String identifier) {
        eventService.deleteEvent(identifier);
        return Response.ok().build();
    }

    @Logging
    public List<EventDashboardRepresentation> getAllAsSummary() {
        return eventService.getAllEventAsSummary();
    }

    @Logging
    public EventRepresentation get(String identifier) {
        return eventService.getEvent(identifier);
    }

    @Logging
    public List<EventRepresentation> getAll() {
        return eventService.getAllEvents();
    }

    @Override
    public EventRepresentation license(String identifier, String license) {
        return eventService.setLicense(identifier, license);
    }

    @Override
    public EventRepresentation addModule(String identifier, String module) {
        return eventService.addModule(identifier, module);
    }

    @Override
    public EventRepresentation removeModule(String identifier, String module) {
        return eventService.removeModule(identifier, module);
    }

    @Logging
    public EventRepresentation publish(String identifier) {
        return eventService.publishEvent(identifier);
    }

    @Logging
    public EventRepresentation archive(String identifier) {
        return eventService.archiveEvent(identifier);
    }

    @Logging
    public EventRepresentation activateTestMode(String identifier) {
        return eventService.setTestMode(identifier, true);
    }

    @Logging
    public EventRepresentation deactivateTestMode(String identifier) {
        return eventService.setTestMode(identifier, false);
    }

    @Logging
    public EventRepresentation close(String identifier) {
        return eventService.closeEvent(identifier);
    }

    @Logging
    public EventRepresentation reopen(String identifier) {
        return eventService.reopenEvent(identifier);
    }

    @Logging
    public EventFeatureRepresentation addEventFeature(String identifier, EventFeatureCreationRequest eventFeatureCreationRequest) {
        return eventService.addEventFeature(identifier, eventFeatureCreationRequest);
    }

}
