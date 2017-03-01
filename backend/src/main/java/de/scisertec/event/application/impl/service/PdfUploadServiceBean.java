package de.scisertec.event.application.impl.service;

import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.application.api.mail.MailService;
import de.scisertec.core.application.api.template.TemplateService;
import de.scisertec.core.application.impl.helper.CsvHelper;
import de.scisertec.core.application.impl.mail.MailAttachment;
import de.scisertec.core.application.impl.mail.MailReceiver;
import de.scisertec.core.infrastructure.qualifier.Active;
import de.scisertec.core.infrastructure.qualifier.template.TextTemplate;
import de.scisertec.event.application.api.model.command.EventLogoUploadCommand;
import de.scisertec.event.application.api.model.command.PdfUploadCommand;
import de.scisertec.event.application.api.model.representation.EventLogoRepresentation;
import de.scisertec.event.application.api.service.EventLogoService;
import de.scisertec.event.application.api.service.PdfUploadService;
import de.scisertec.event.application.impl.model.representation.EventLogoToRepresentation;
import de.scisertec.event.domain.model.Event;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;
import de.scisertec.event.infrastructure.repository.EventRepository;
import de.scisertec.event.infrastructure.repository.ParticipantRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Locale;

@ApplicationScoped
public class PdfUploadServiceBean implements PdfUploadService {

    @Inject
    EventRepository eventRepository;

    @Inject
    ConfigurationManager configurationManager;

    @Inject
    MailService mailService;

    @Inject
    @Active
    Instance<Locale> locale;

    @Override
    public void uploadPdfDocument(String identifier, PdfUploadCommand pdfUploadCommand) {
        Event event = eventRepository.findByIdentifier(identifier);

        mailService.newMail().setSender("noreply@scisertec.com", "Team SciSerTec")
                .setReceiver(new MailReceiver("team@vcongress.de").getValue())
                .addBcc(new MailReceiver("info@vcongress.de").getValue())
                .setTextContent("Non-for-profit Dokument für die Veranstaltung\n" + event.name() +
                        "\nDatum der Veranstaltung: " + event.dateRangeAsString())
                .setHtmlContent("Non-for-profit Dokument für die Veranstaltung<br/>" + event.name() +
                        "<br/>Datum der Veranstaltung: " + event.dateRangeAsString())
                .setTopic("vCongress CMS (Online Shop) - " + event.identifier() + " - Non-for-profit Dokument")
                .addAttachment(new MailAttachment() {
                    @Override
                    public String getFileName() {
                        return "non-for-profit_" + event.identifier() + ".pdf";
                    }

                    @Override
                    public String getDescription() {
                        return "Lizenzbestätigung für non-for-profit";
                    }

                    @Override
                    public String getContentType() {
                        return "application/pdf";
                    }

                    @Override
                    public byte[] getContent() {
                        return pdfUploadCommand.getData();
                    }
                })
                .sendMail();
    }
}
