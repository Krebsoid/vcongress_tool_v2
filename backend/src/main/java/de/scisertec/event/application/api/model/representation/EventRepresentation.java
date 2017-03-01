package de.scisertec.event.application.api.model.representation;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.application.api.model.representation.Representation;
import de.scisertec.event.domain.model.EventLicense;
import de.scisertec.event.domain.model.EventModule;
import de.scisertec.event.domain.model.Participation;

import java.util.List;

public interface EventRepresentation extends Representation {

    Long getId();
    String getName();
    String getStartDate();
    String getEndDate();
    String getStartTime();
    String getEndTime();
    String getLocation();
    String getDescription();
    String getWebsite();
    String getAdditionalInformation();
    String getIdentifier();
    String getEventStatus();
    Boolean getPublished();
    Boolean getShortVersion();
    String getLink();
    String getContact();

    List<String> getEventModules();
    String getEventLicense();

    List<EventFeatureRepresentation> getEventFeatures();
    EventLogoRepresentation getEventLogo();

    void makeParticipationDependant(Participation participation);
    void setUser(User user);

}
