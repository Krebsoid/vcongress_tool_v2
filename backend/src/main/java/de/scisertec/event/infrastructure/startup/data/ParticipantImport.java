package de.scisertec.event.infrastructure.startup.data;

import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.core.application.api.data.DataImportUnit;
import de.scisertec.event.domain.model.EventParticipationFeature;
import de.scisertec.event.domain.model.Participation;
import de.scisertec.event.domain.model.event.PersonEventRegistration;
import de.scisertec.event.domain.model.feature.EventFeatureCategory;
import de.scisertec.event.infrastructure.repository.ParticipantRepository;
import de.scisertec.person.domain.factory.PersonFactory;
import de.scisertec.person.domain.model.Gender;
import de.scisertec.person.domain.model.Person;
import de.scisertec.person.infrastructure.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ParticipantImport extends DataImportUnit {

    @Inject
    GroupRepository groupRepository;

    @Inject
    EventImport eventImport;

    @Inject
    ParticipantRepository participantRepository;

    @Inject
    PersonRepository personRepository;

    @Inject
    PersonFactory personFactory;

    Participation participation;

    @Override
    protected Class importUnitClass() {
        return this.getClass();
    }

    protected void initialize() {

        Person person = personFactory.create("test@test.de", "test", "Dr.",
                "Peter", "Mustermann", Gender.MALE, "Musterstraße 17",
                "12345", "Musterstadt", "Sachsen", "DE",
                "Dr. Peter Mustermann", "MHH", "Musterstraße 17", "12345", "Musterstadt",
                "Sachsen", "DE",
                "0170 12345678", "0170 12345679",
                "MHH", "", "CEO",
                Boolean.TRUE);
        person.user().addRelationship(groupRepository.findByIdentifier("test"));
        personRepository.save(person);
        Participation participation = Participation.create(person, eventImport.getEvent());
        participantRepository.save(participation);
        eventImport.getEvent().eventFeatures(EventFeatureCategory.PARTICIPATION).forEach(eventFeature ->
            {
                if (((EventParticipationFeature) eventFeature).hasDefaultSelection()) {
                    participation.eventSelections().put(eventFeature.getId(), ((EventParticipationFeature) eventFeature).defaultSelection()
                            .participation(participation));
                }
            }
        );
        participantRepository.save(participation);

    }

    public Participation getParticipation() {
        return participation;
    }
}
