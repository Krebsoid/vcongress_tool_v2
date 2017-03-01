package de.scisertec.core.application.api.mail;

public interface Configuration {

    String getHostName();
    String getLogin();
    String getPassword();

    String getSourceName();
    String getSourceMailAddress();

}
