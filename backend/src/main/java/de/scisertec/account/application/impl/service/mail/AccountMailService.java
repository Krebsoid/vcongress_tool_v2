package de.scisertec.account.application.impl.service.mail;

import de.scisertec.account.domain.model.event.UserEnable;
import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.application.api.mail.MailService;
import de.scisertec.core.application.api.template.TemplateService;
import de.scisertec.core.application.impl.mail.MailReceiver;
import de.scisertec.core.infrastructure.qualifier.template.TextTemplate;
import de.scisertec.person.domain.model.event.PersonRegistration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import java.io.InputStream;

@ApplicationScoped
public class AccountMailService {

    @Inject
    @TextTemplate
    TemplateService<String> templateService;

    @Inject
    MailService mailService;

    @Inject
    ConfigurationManager configurationManager;

    public void onUserEnabled(@Observes(during = TransactionPhase.AFTER_SUCCESS) UserEnable userEnable) {
        /*if(userEnable.user().enabled()) {
            InputStream resourceAsStream = getClass().getResourceAsStream("/META-INF/mail-templates/account/account_enabled_plain_de_DE.ftl");

            mailService.newMail().setSender("noreply@scisertec.com", "Team SciSerTec")
                    .setReceiver(new MailReceiver(userEnable.user().name()).getValue())
                    .setTextContent(templateService.processTemplate(resourceAsStream, () -> null))
                    .setTopic("vCongress Account aktiviert")
                    .sendMail();
        }*/
    }

    public void onNewRegistration(@Observes(during = TransactionPhase.AFTER_SUCCESS) PersonRegistration personRegistration) {
        InputStream resourceAsStream = getClass().getResourceAsStream("/META-INF/mail-templates/account/new_registration_plain_de_DE.ftl");
        mailService.newMail().setSender("noreply@scisertec.com", "Team SciSerTec")
                .setReceiver(new MailReceiver(configurationManager.getProperty("mail.receiver")).getValue())
                .setTextContent(templateService.processTemplate(resourceAsStream, personRegistration))
                .setTopic("Neue Registrierung im Administrationsbereich")
                .sendMail();
    }
}
