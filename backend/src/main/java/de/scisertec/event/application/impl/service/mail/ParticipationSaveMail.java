package de.scisertec.event.application.impl.service.mail;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.core.application.api.mail.MailService;
import de.scisertec.core.application.api.template.TemplateService;
import de.scisertec.core.application.impl.mail.MailAttachment;
import de.scisertec.core.application.impl.mail.MailReceiver;
import de.scisertec.core.infrastructure.qualifier.Active;
import de.scisertec.core.infrastructure.qualifier.TestEntity;
import de.scisertec.core.infrastructure.qualifier.template.TextTemplate;
import de.scisertec.event.domain.model.event.MailFeatureTest;
import de.scisertec.event.domain.model.event.ParticipationSave;
import de.scisertec.event.domain.model.feature.EventFeatureType;
import de.scisertec.person.domain.model.Person;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@RequestScoped
public class ParticipationSaveMail {

    @Inject
    MailService mailService;

    @Inject
    @TextTemplate
    TemplateService<String> templateService;

    @Inject
    @Active
    Instance<Locale> locale;

    @Inject
    @BundlePath("mails")
    Instance<LocalePropertyManager> localePropertyManager;


    public void onParticipationSave(@Observes ParticipationSave participationSave) {
        String registrationMail;
        String topic;
        if(participationSave.method().equals("withCost")) {
            registrationMail = "registration";
            topic = localePropertyManager.get().getLocalizedProperty("app.event.registration.mail.topic");
        } else {
            registrationMail = "registration_short";
            topic = localePropertyManager.get().getLocalizedProperty("app.event.registration.short.mail.topic");
        }

        InputStream plainTemplate = getClass().getResourceAsStream("/META-INF/mail-templates/event/registration_plain_"+ locale.get().toString() +".ftl");
        InputStream htmlTemplate = getClass().getResourceAsStream("/META-INF/mail-templates/event/" + registrationMail + "_html_"+ locale.get().toString() +".ftl");
        InputStream termsAndConditions = getClass().getResourceAsStream("/META-INF/mail-templates/event/SciSerTec_AGBs_vCongress_"+ locale.get().toString() +".pdf");

        mailService.newMail().setSender("support@vcongress.de", "vCongress Support")
                .setReceiver(new MailReceiver(participationSave.person.user().name()).getValue())
                .setTextContent(templateService.processTemplate(plainTemplate, participationSave))
                .setHtmlContent(templateService.processTemplate(htmlTemplate, participationSave))
                .addAttachment(new MailAttachment() {
                    @Override
                    public String getFileName() {
                        return "AGB_UKL_vCongress.pdf";
                    }

                    @Override
                    public String getDescription() {
                        return "Allgemeine Geschäftsbedingungen des UKL zu der Nutzung von vCongress";
                    }

                    @Override
                    public String getContentType() {
                        return "application/pdf";
                    }

                    @Override
                    public byte[] getContent() {
                        try {
                            return IOUtils.toByteArray(termsAndConditions);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .setTopic(participationSave.event.name() + " - " + topic)
                .sendMail();
    }


    @Inject
    @TestEntity
    Person person;

    public void onTestSend(@Observes MailFeatureTest mailFeatureTest) {
        if(mailFeatureTest.eventFeature().eventFeatureType().equals(EventFeatureType.MAIL_REGISTRATION)) {
            ParticipationSave participationSave = new ParticipationSave();
            participationSave.event = mailFeatureTest.eventFeature().event();
            person.user().name(mailFeatureTest.getReceiver());
            participationSave.person = person;
            participationSave.method = "withCost";
            this.onParticipationSave(participationSave);
        }
    }

}
