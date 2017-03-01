package de.scisertec.account.application.impl.service;

import de.scisertec.account.application.api.model.representation.GroupRepresentation;
import de.scisertec.account.application.api.model.representation.RoleRepresentation;
import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.application.api.service.Permissions;
import de.scisertec.account.application.impl.model.representation.GroupToRepresentation;
import de.scisertec.account.application.impl.model.representation.RoleToRepresentation;
import de.scisertec.account.application.impl.model.representation.UserToStateRepresentation;
import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.RoleRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class PermissionsBean implements Permissions {

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    @Inject
    GroupRepository groupRepository;

    @Override
    public List<RoleRepresentation> getRoles(String groupName) {
        Group group = groupRepository.findByIdentifier(groupName);
        return group.roles().stream().map(RoleToRepresentation::new).collect(Collectors.toList());
    }

    @Override
    public List<GroupRepresentation> getGroups() {
        return groupRepository.findAll().stream().map(GroupToRepresentation::new).collect(Collectors.toList());
    }

    @Override
    public UserStateRepresentation addRole(Long userId, String groupName, String roleName) {
        User user = userRepository.findBy(userId);
        Group group = groupRepository.findByIdentifier(groupName);
        user.addRole(group, group.roles().stream().filter(role -> role.name().equals(roleName)).findFirst().get());
        return new UserToStateRepresentation(user);
    }

    @Override
    public UserStateRepresentation removeRole(Long userId, String groupName, String roleName) {
        User user = userRepository.findBy(userId);
        Group group = groupRepository.findByIdentifier(groupName);
        user.removeRole(group, group.roles().stream().filter(role -> role.name().equals(roleName)).findFirst().get());
        return new UserToStateRepresentation(user);
    }
}
