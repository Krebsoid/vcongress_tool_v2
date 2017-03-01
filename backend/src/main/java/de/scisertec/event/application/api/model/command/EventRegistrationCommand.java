package de.scisertec.event.application.api.model.command;

import de.scisertec.core.application.api.model.command.Command;

public interface EventRegistrationCommand extends Command {

    String getGroup();

    String getEmail();
    String getPassword();

    String getGender();
    String getTitle();

    String getFirstName();
    String getLastName();

    String getStreet();
    String getZipCode();
    String getCity();
    String getState();
    String getCountry();

    String getInvoiceFullName();
    String getInvoiceInstitute();
    String getInvoiceStreet();
    String getInvoiceZipCode();
    String getInvoiceCity();
    String getInvoiceState();
    String getInvoiceCountry();

    String getFax();
    String getPhone();

    String getInstitute();
    String getDepartment();
    String getPosition();

    Boolean getNotificationRequest();

}
