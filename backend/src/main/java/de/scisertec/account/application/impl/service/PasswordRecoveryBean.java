package de.scisertec.account.application.impl.service;

import de.scisertec.account.application.api.model.command.ExecutePasswordRecoveryCommand;
import de.scisertec.account.application.api.model.command.InitializePasswordRecoveryCommand;
import de.scisertec.account.application.api.service.PasswordRecovery;
import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.PasswordRecoveryToken;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.PasswordRecoveryTokenRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.domain.model.event.PasswordChangeInit;
import de.scisertec.account.domain.model.event.PasswordChanged;
import de.scisertec.core.application.api.mail.MailService;
import de.scisertec.core.application.api.template.TemplateService;
import de.scisertec.core.application.impl.mail.MailReceiver;
import de.scisertec.core.infrastructure.qualifier.template.TextTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.InputStream;

@ApplicationScoped
@Transactional
public class PasswordRecoveryBean implements PasswordRecovery {

    @Inject
    PasswordRecoveryTokenRepository passwordRecoveryTokenRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    GroupRepository groupRepository;

    @Inject
    MailService mailService;

    @Inject
    @TextTemplate
    TemplateService<String> templateService;

    public void onPasswordChangeToken(@Observes(during = TransactionPhase.AFTER_SUCCESS) PasswordChangeInit passwordChangeInit) {
        InputStream resourceAsStream = getClass().getResourceAsStream("/META-INF/mail-templates/account/password_recovery_plain_de_DE.ftl");

        mailService.newMail().setSender("noreply@scisertec.com", "Team SciSerTec")
                .setReceiver(new MailReceiver(passwordChangeInit.user().name()).getValue())
                .setTextContent(templateService.processTemplate(resourceAsStream, passwordChangeInit))
                .setTopic("vCongress Passwort Wiederherstellung")
                .sendMail();
    }

    public void changePassword(ExecutePasswordRecoveryCommand command) {
        PasswordRecoveryToken passwordRecoveryToken = passwordRecoveryTokenRepository.findByValue(command.getToken());
        Group group = groupRepository.findByIdentifier(command.getGroup());
        User user = userRepository.findByGroupAndMailAddress(group, command.getMail());
        user.credential().password(command.getNewPassword()).save();
        PasswordChanged.create(user, passwordRecoveryToken, command.getNewPassword(), user.credential().password(), command.getGroup());
    }

    public void initiatePasswordChange(InitializePasswordRecoveryCommand command) {
        Group group = groupRepository.findByIdentifier(command.getGroup());
        User user = userRepository.findByGroupAndMailAddress(group, command.getMail());
        PasswordChangeInit.create(user, command.getGroup());
    }
}
