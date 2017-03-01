package de.scisertec.core.application.impl.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import java.io.Serializable;

@Singleton
public class MailWorker implements Serializable {

    @Asynchronous
    @Lock(LockType.READ)
    public void sendMail(HtmlEmail htmlEmail) {
        try {
            htmlEmail.send();
        } catch (EmailException | RuntimeException e) {
            e.printStackTrace();
        }
    }

}
