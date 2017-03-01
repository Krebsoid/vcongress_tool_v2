package de.scisertec.core.application.api.mail;

import de.scisertec.core.application.impl.mail.MailAttachment;

public interface MailService {

    void sendMail();

    MailService newMail();

    MailService setTextContent(String content);
    MailService setHtmlContent(String content);

    MailService setTopic(String topic);

    MailService setReceiver(String receiverMailAddress);
    MailService setReceiver(String receiverMailAddress, String receiverName);
    MailService addBcc(String receiverMailAddress);
    MailService setSender(String senderMailAddress);
    MailService setSender(String senderMailAddress, String senderName);

    MailService addAttachment(MailAttachment mailAttachment);

}
