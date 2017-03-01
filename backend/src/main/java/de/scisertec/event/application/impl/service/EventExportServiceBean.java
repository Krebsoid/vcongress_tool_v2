package de.scisertec.event.application.impl.service;

import de.scisertec.event.application.api.service.EventExportService;
import de.scisertec.event.domain.model.*;
import de.scisertec.event.domain.model.feature.DinnerFeature;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.event.infrastructure.repository.EventRepository;
import de.scisertec.event.infrastructure.repository.ParticipantRepository;
import org.joda.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class EventExportServiceBean implements EventExportService {

    @Inject
    EventRepository eventRepository;

    @Inject
    ParticipantRepository participantRepository;

    @Override
    public String getParticipantsAsCSV(String identifier) {
        Event event = eventRepository.findByIdentifier(identifier);
        List<Participation> participations = participantRepository.findAllByEvent(event);
        StringBuilder stringBuilder = new StringBuilder();
        buildHeader(event, stringBuilder);
        for(Participation participation : participations) {
            stringBuilder.append("\"").append(participation.person().title()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().firstName()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().lastName()).append("\"").append(";");
            String gender = participation.person().gender() != null ? participation.person().gender().name() : "";
            stringBuilder.append(gender).append(";");

            stringBuilder.append("\"").append(participation.person().address().street()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().address().city()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().address().zipCode()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().address().state()).append("\"").append(";");
            String country = participation.person().address().country() != null ? participation.person().address().country().name() : "";
            stringBuilder.append("\"").append(country).append("\"").append(";");

            stringBuilder.append("\"").append(participation.person().user().name()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().contact().phone()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().contact().fax()).append("\"").append(";");

            stringBuilder.append("\"").append(participation.person().occupation().position()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().occupation().institute()).append("\"").append(";");
            stringBuilder.append("\"").append(participation.person().occupation().department()).append("\"").append(";");
            stringBuilder.append(LocalDate.fromCalendarFields(participation.dateOfRegistration()).toString()).append(";");
            stringBuilder.append(participation.active() ? "Nein" : "Ja").append(";");
            stringBuilder.append(participation.person().notificationOptions() != null && participation.person().notificationOptions().notificationRequest() ? "Ja" : "Nein").append(";");

            List<EventSelection> eventSelections = participation.eventSelections().values().stream()
                    .sorted((o1, o2) -> o1.eventFeature().getId().compareTo(o2.eventFeature().getId()))
                    .collect(Collectors.toList());
            for(EventSelection eventSelection : eventSelections) {
                stringBuilder.append("\"").append(eventSelection.value()).append("\"").append(";");
            }
            stringBuilder.append("\"").append(participation.note().replace('\n',' ')).append("\"").append(";");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private StringBuilder buildHeader(Event event, StringBuilder stringBuilder) {
        stringBuilder.append("Titel;Vorname;Nachname;Geschlecht;");
        stringBuilder.append("Straße;Stadt;PLZ;Bundesland;Land;");
        stringBuilder.append("E-Mail Adresse;Telefon;Fax;");
        stringBuilder.append("Position;Einrichtung;Abteilung;Datum der Registrierung;Abgemeldet;Benachrichtigungsanfrage;");
        List<EventFeature> eventFeatures = event.eventFeatures(EventFeatureCategory.PARTICIPATION).stream()
                .sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .collect(Collectors.toList());
        for(EventFeature eventFeature : eventFeatures) {
            if(eventFeature.eventFeatureType().equals(EventFeatureType.PARTICIPANT_STATUS) || eventFeature.eventFeatureType().equals(EventFeatureType.SELECTION)) {
                stringBuilder.append(((EventParticipationFeature)eventFeature).label()).append(";");
            }
            if(eventFeature.eventFeatureType().equals(EventFeatureType.DINNER)) {
                stringBuilder.append(((DinnerFeature)eventFeature).eventItem().name()).append(";");
            }
        }
        stringBuilder.append("Notiz;");
        stringBuilder.append("\n");
        return stringBuilder;
    }


}
