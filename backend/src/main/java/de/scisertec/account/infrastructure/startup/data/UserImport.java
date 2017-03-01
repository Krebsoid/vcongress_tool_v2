package de.scisertec.account.infrastructure.startup.data;

import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.Relationship;
import de.scisertec.account.domain.model.Role;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.domain.factory.GroupFactory;
import de.scisertec.account.domain.factory.UserFactory;
import de.scisertec.core.application.api.data.DataImportUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashSet;

@ApplicationScoped
public class UserImport extends DataImportUnit {

    @Inject
    UserFactory userFactory;
    @Inject
    UserRepository userRepository;

    @Inject
    GroupFactory groupFactory;
    @Inject
    GroupRepository groupRepository;

    Group system;

    User user;
    User customer;
    User admin;

    Role userRole;
    Role customerRole;
    Role adminRole;

    @Override
    protected Class importUnitClass() {
        return this.getClass();
    }

    protected void initialize() {
        userRole = Role.create("USER");
        customerRole = Role.create("CUSTOMER");
        adminRole = Role.create("ADMIN");

        system = groupFactory.create()
                .name("System")
                .identifier("system")
                .addRole(userRole)
                .addRole(customerRole)
                .addRole(adminRole)
                .defaultRole(userRole);
        groupRepository.save(system);

        HashSet<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        HashSet<Role> customerRoles = new HashSet<>();
        customerRoles.add(customerRole);
        customerRoles.add(userRole);

        admin = userFactory.create("admin@admin.de", "admin");
        admin.relationships().clear();
        admin.addRelationship(Relationship.create(system, adminRoles));
        userRepository.save(admin);

        customer = userFactory.create("customer@customer.de", "customer");
        customer.relationships().clear();
        customer.addRelationship(Relationship.create(system, customerRoles));
        userRepository.save(customer);

        user = userFactory.create("user@user.de", "user");
        userRepository.save(user);
    }

    public User getUser() {
        return user;
    }

    public User getCustomer() {
        return customer;
    }

    public User getAdmin() {
        return admin;
    }
}
