package ceni.system.votesync.service.entity.user;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.Role;
import ceni.system.votesync.model.entity.User;
import ceni.system.votesync.service.spec.auth.util.AbstractPasswordHashing;
import ceni.system.votesync.dto.request.user.NewUserRequest;
import ceni.system.votesync.service.PasswordHashingService;
import ceni.system.votesync.service.entity.role.RoleService;
import ceni.system.votesync.exception.RoleNotFoundException;

@Service
public class UserFactory {

    private RoleService roleService;
    private PasswordHashingService passwordHashingService;

    public UserFactory(RoleService roleService, PasswordHashingService passwordHashingService) {
        this.roleService = roleService;
        this.passwordHashingService = passwordHashingService;
    }

    public User createUser(NewUserRequest newUserRequest) {
        User newUser = new User();
        Role role = this.roleService.getRoleById(newUserRequest.getRoleId());
        if (role == null) {
            throw new RoleNotFoundException("Role not found. Role id: " + newUserRequest.getRoleId());
        }
        AbstractPasswordHashing passwordHashing = this.passwordHashingService.selectPasswordHashingStrategy(role);
        newUser.setName(newUserRequest.getName());
        newUser.setFirstName(newUserRequest.getFirstName());
        newUser.setIdentifier(newUserRequest.getIdentifier());
        newUser.setContact(newUserRequest.getContact());
        newUser.setRole(role);
        newUser.setPassword(passwordHashing.hash(newUserRequest.getPassword()));
        return newUser;
    }
}
