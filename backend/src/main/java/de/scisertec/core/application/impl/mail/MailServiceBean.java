package de.scisertec.core.application.impl.mail;

import de.scisertec.core.application.api.environment.ConfigurationManager;
import de.scisertec.core.application.api.mail.MailService;
import de.scisertec.core.application.api.template.TemplateService;
import de.scisertec.core.infrastructure.qualifier.template.TextTemplate;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MailServiceBean implements MailService {

    @Inject
    ConfigurationManager configurationManager;

    String contentText;
    String contentHtml;
    String topic;

    String receiverName;
    String receiverMailAddress;

    String senderName;
    String senderMailAddress;

    List<String> bccReceiverMailAddresses = new ArrayList<>();

    List<MailAttachment> mailAttachments = new ArrayList<>();

    @Inject
    @TextTemplate
    TemplateService<String> templateService;

    @Inject
    MailWorker mailWorker;

    public void sendMail() {
        HtmlEmail htmlEmail = new HtmlEmail();

        String hostname = configurationManager.getProperty("mail.host");
        String user = configurationManager.getProperty("mail.login");
        String password = configurationManager.getProperty("mail.password");

        if(user != null && password != null) {
            htmlEmail.setAuthentication(user, password);
        }

        htmlEmail.setSmtpPort(Integer.parseInt(configurationManager.getProperty("mail.port")));
        htmlEmail.setHostName(hostname);

        try {
            htmlEmail.addTo(receiverMailAddress, receiverName);

            if(!bccReceiverMailAddresses.isEmpty()) {
                for(String bcc : bccReceiverMailAddresses) {
                    htmlEmail.addBcc(bcc);
                }
            }

            htmlEmail.setFrom(senderMailAddress, senderName);
            if(!contentHtml.equals("")) {
                htmlEmail.setContent(null, "text/html; charset=UTF-8");
                htmlEmail.setHtmlMsg(contentHtml);
                if(!contentText.equals("")) {
                    htmlEmail.setTextMsg(contentText);
                }
            }
            else if(!contentText.equals("")) {
                htmlEmail.setContent(contentText, "text/plain; charset=UTF-8");
            }
            else {
                throw new RuntimeException("No content supplied");
            }

            htmlEmail.setSubject(topic);

            for(MailAttachment mailAttachment : mailAttachments) {
                try {
                    htmlEmail.attach(new ByteArrayDataSource(mailAttachment.getContent(), mailAttachment.getContentType()),
                            mailAttachment.getFileName(), mailAttachment.getDescription(),
                            EmailAttachment.ATTACHMENT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mailWorker.sendMail(htmlEmail);

        } catch (EmailException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    public MailService newMail() {
        this.mailAttachments.clear();
        this.bccReceiverMailAddresses.clear();
        this.contentHtml = "";
        this.contentText = "";
        this.receiverMailAddress = "";
        this.receiverName = "";
        this.senderMailAddress = "";
        this.senderName = "";
        this.topic = "";
        return this;
    }

    public MailService setTextContent(String content) {
        this.contentText = content;
        return this;
    }


    public MailService setHtmlContent(String content) {
        this.contentHtml = content;
        return this;
    }


    public MailService setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MailService setReceiver(String receiverMailAddress) {
        this.receiverMailAddress = new MailReceiver(receiverMailAddress).getValue();
        this.receiverName = receiverMailAddress;
        return this;
    }

    public MailService setReceiver(String receiverMailAddress, String receiverName) {
        this.receiverMailAddress = new MailReceiver(receiverMailAddress).getValue();
        this.receiverName = receiverName;
        return this;
    }

    public MailService addBcc(String receiverMailAddress) {
        bccReceiverMailAddresses.add(receiverMailAddress);
        return this;
    }

    public MailService setSender(String senderMailAddress) {
        this.senderMailAddress = senderMailAddress;
        this.senderName = senderMailAddress;
        return this;
    }

    public MailService setSender(String senderMailAddress, String senderName) {
        this.senderMailAddress = senderMailAddress;
        this.senderName = senderName;
        return this;
    }

    public MailService addAttachment(MailAttachment mailAttachment) {
        this.mailAttachments.add(mailAttachment);
        return this;
    }
}
