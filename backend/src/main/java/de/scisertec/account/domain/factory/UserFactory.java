package de.scisertec.account.domain.factory;

import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.domain.model.*;
import de.scisertec.core.domain.factory.Factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;

@ApplicationScoped
public class UserFactory implements Factory {

    @Inject
    GroupRepository groupRepository;

    public User create() {
        Credential credential = new Credential();
        return buildUser(credential);
    }

    public User create(String username) {
        Credential credential = new Credential();
        credential.username(username);
        credential.randomPassword();
        return buildUser(credential);
    }

    public User create(String username, String password) {
        Credential credential = new Credential();
        credential.username(username);
        credential.password(password);
        return buildUser(credential);
    }

    public User createWithoutGroup(String username, String password) {
        Credential credential = new Credential();
        credential.username(username);
        credential.password(password);
        User user = new User();
        user.credential(credential);
        return user;
    }

    public User createWithoutGroup(String username) {
        Credential credential = new Credential();
        credential.username(username);
        credential.randomPassword();
        User user = new User();
        user.credential(credential);
        return user;
    }

    private User buildUser(Credential credential) {
        User user = new User();
        user.credential(credential);
        Group system = groupRepository.findByIdentifier("system");
        Relationship relationship = Relationship.create(system, new HashSet<>(Collections.singletonList(system.defaultRole())));
        user.addRelationship(relationship);
        return user;
    }

}
