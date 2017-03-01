package de.scisertec.core.application.impl.mail;

import de.scisertec.core.application.api.environment.StageWrapper;

public class MailReceiver extends StageWrapper {

    String mailAddress;

    public MailReceiver(String mailAddress) {
        super();
        this.mailAddress = mailAddress;
    }

    @Override
    public String propertyKey() {
        return "mail.test.receiver";
    }

    @Override
    public String originalValue() {
        return mailAddress;
    }
}
