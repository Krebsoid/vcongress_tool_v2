package de.scisertec.person.application.impl.model.representation;


import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.application.impl.model.representation.UserToStateRepresentation;
import de.scisertec.core.application.impl.model.representation.AbstractRepresentation;
import de.scisertec.person.application.api.model.representation.PersonListRepresentation;
import de.scisertec.person.domain.model.Person;

public class PersonToListRepresentation extends AbstractRepresentation implements PersonListRepresentation {
    Long id;
    String firstName;
    String lastName;
    String email;
    Boolean enabled;

    UserStateRepresentation user;

    public PersonToListRepresentation(Person person) {
        this.id = person.getId();
        this.firstName = person.firstName();
        this.lastName = person.lastName();
        this.email = person.user().credential().username();
        this.enabled = person.user().enabled();
        this.user = new UserToStateRepresentation(person.user());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public UserStateRepresentation getUser() {
        return user;
    }
}
