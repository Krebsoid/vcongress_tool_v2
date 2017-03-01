package de.scisertec.core.application.impl.mail;

public interface MailAttachment {

    String getFileName();
    String getDescription();
    String getContentType();

    byte[] getContent();

}
