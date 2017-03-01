package de.scisertec.event.infrastructure.service;

import de.scisertec.core.application.api.mail.MailService;
import de.scisertec.core.application.impl.helper.CsvHelper;
import de.scisertec.core.application.impl.mail.MailAttachment;
import de.scisertec.event.application.api.service.EventExportService;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.domain.model.event.EventCreation;
import de.scisertec.event.domain.model.event.EventDeletion;
import de.scisertec.event.infrastructure.repository.EventRepository;
import de.scisertec.payment.application.api.service.TransactionExportService;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Optional;

@Singleton
public class EventReminderService {

    @Resource
    TimerService timerService;

    @Inject
    EventRepository eventRepository;

    public void onEventCreation(@Observes EventCreation eventCreation) {
        Calendar startDate = eventCreation.event().startDate();
        startDate.add(Calendar.WEEK_OF_YEAR, 4);
        timerService.createTimer(startDate.getTime(), eventCreation.event().identifier());
    }

    public void onEventDeletion(@Observes EventDeletion eventDeletion) {
        Optional<Timer> eventTimer = timerService.getTimers().stream().filter(timer -> timer.getInfo().toString().equals(eventDeletion.event().identifier())).findFirst();
        if(eventTimer.isPresent()) {
            eventTimer.get().cancel();
        }
    }

    @Inject
    MailService mailService;

    @Inject
    EventExportService eventExportService;

    @Inject
    TransactionExportService transactionExportService;

    @Timeout
    @Lock(LockType.READ)
    public synchronized void onEventExpiredNotificationTimeout(Timer timer) {
        Event event = eventRepository.findByIdentifier(timer.getInfo().toString());

        String participantsAsCSV = eventExportService.getParticipantsAsCSV(event.identifier());
        String transactionsAsCSV = transactionExportService.getTransactionsAsCSV(event.identifier());

        mailService.newMail().setSender("noreply@scisertec.com", "Team SciSerTec")
                .setReceiver("team@vcongress.de")
                .addBcc("info@vcongress.de")
                .setTextContent(event.name() + " - " + event.participants().size() + " participants\n" +
                        "Datum der Veranstaltung: " + event.dateRangeAsString())
                .setHtmlContent(event.name() + " - " + event.participants().size() + " participants<br/>" +
                        "Datum der Veranstaltung: " + event.dateRangeAsString())
                .setTopic("vCongress CMS (Online Shop) - " + event.identifier() + " - Veranstaltungsbenachrichtigung")
                .addAttachment(new MailAttachment() {
                    @Override
                    public String getFileName() {
                        return "participant_list_" + event.identifier() + ".csv";
                    }

                    @Override
                    public String getDescription() {
                        return "Teilnehmerliste";
                    }

                    @Override
                    public String getContentType() {
                        return "text/csv";
                    }

                    @Override
                    public byte[] getContent() {
                        return CsvHelper.convertToByteArrayWithUTF8(participantsAsCSV);
                    }
                })
                .addAttachment(new MailAttachment() {
                    @Override
                    public String getFileName() {
                        return "transaction_list_" + event.identifier() + ".csv";
                    }

                    @Override
                    public String getDescription() {
                        return "Transaktionenliste";
                    }

                    @Override
                    public String getContentType() {
                        return "text/csv";
                    }

                    @Override
                    public byte[] getContent() {
                        return CsvHelper.convertToByteArrayWithUTF8(transactionsAsCSV);
                    }
                })
                .sendMail();
    }

}
