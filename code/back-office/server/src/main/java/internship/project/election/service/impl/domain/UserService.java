package internship.project.election.service.impl.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import internship.project.election.config.Authority;
import internship.project.election.dto.request.NewUserRequest;
import internship.project.election.model.Role;
import internship.project.election.model.User;
import internship.project.election.repository.UserRepository;
import internship.project.election.service.impl.auth.util.AdminPasswordHashing;
import internship.project.election.service.impl.auth.util.UserPasswordHashing;
import internship.project.election.service.impl.domain.specification.UserSpecification;
import internship.project.election.service.spec.auth.util.AbstractPasswordHashing;

@Service
public class UserService {

    private UserRepository repository;

    private RoleService roleService;

    private AdminPasswordHashing adminPasswordHashing;

    private UserPasswordHashing userPasswordHashing;

    public UserService(UserRepository repository, RoleService roleService, AdminPasswordHashing adminPasswordHashing,
            UserPasswordHashing userPasswordHashing) {
        this.repository = repository;
        this.roleService = roleService;
        this.adminPasswordHashing = adminPasswordHashing;
        this.userPasswordHashing = userPasswordHashing;
    }

    public Page<User> getAllUsers(int page, int size) {
        // Specification<User> spec = UserSpecification.getActiveUsers();
        return this.repository.findAll(PageRequest.of(page, size));
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
            passwordHashing = this.adminPasswordHashing;
        } else {
            passwordHashing = this.userPasswordHashing;
        }
        newUser.setName(request.getName());
        newUser.setFirstName(request.getFirstName());
        newUser.setIdentifier(request.getIdentifier());
        newUser.setContact(request.getContact());
        newUser.setRole(role);
        newUser.setPassword(passwordHashing.hash(request.getPassword()));
        this.saveUser(newUser);
    }
}
