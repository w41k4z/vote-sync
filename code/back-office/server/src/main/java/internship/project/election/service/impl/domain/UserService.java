package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import internship.project.election.config.Authority;
import internship.project.election.domain.Role;
import internship.project.election.domain.User;
import internship.project.election.dto.request.NewUserRequest;
import internship.project.election.repository.UserRepository;
import internship.project.election.service.impl.auth.util.AdminPasswordHashing;
import internship.project.election.service.impl.auth.util.UserPasswordHashing;
import internship.project.election.service.impl.domain.filter.UserFilter;
import internship.project.election.service.spec.AuthService;
import internship.project.election.service.spec.util.AbstractPasswordHashing;

@Service
public class UserService {

    private PasswordEncoder encoder;

    private UserRepository repository;

    private RoleService roleService;

    public UserService(PasswordEncoder encoder, UserRepository repository, RoleService roleService) {
        this.encoder = encoder;
        this.repository = repository;
        this.roleService = roleService;
    }

    public List<User> getAllUsers() {
        UserDetails activeUser = AuthService.getActiveUser();
        Role role = new Role(activeUser.getAuthorities().stream().findFirst().get().getAuthority());
        Specification<User> spec = UserFilter.getActiveUsers();
        if (!role.getName().equals(Authority.ADMIN.toString())) {
            spec = spec.and(UserFilter.getNonAdminUsers());
        }
        return this.repository.findAll(spec);
    }

    public User getUserById(int id) {
        return this.repository.findById(id).orElse(null);
    }

    public User getUserByIdentifier(String identifier) {
        return this.repository.findByIdentifier(identifier).orElse(null);
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public void deleteUser(Integer id) {
        this.repository.deleteById(id);
    }

    public void createNewUser(NewUserRequest request) {
        AbstractPasswordHashing passwordHashing;
        User newUser = new User();
        Role role = this.roleService.getRoleById(request.getRoleId());
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        if (role.getName().equals(Authority.ADMIN.toString())) {
            passwordHashing = new AdminPasswordHashing(encoder);
        } else {
            passwordHashing = new UserPasswordHashing();
        }
        newUser.setName(request.getName());
        newUser.setFirstName(request.getFirstName());
        newUser.setIdentifier(request.getIdentifier());
        newUser.setContact(request.getContact());
        newUser.setRole(role);
        newUser.setPassword(passwordHashing.getFormattedPassword(request.getPassword()));
        this.saveUser(newUser);
    }
}
