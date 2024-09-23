package internship.project.election.service.impl.domain;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import internship.project.election.config.Authority;
import internship.project.election.dto.request.user.NewUserRequest;
import internship.project.election.dto.request.user.UpdateUserRequest;
import internship.project.election.model.Role;
import internship.project.election.model.User;
import internship.project.election.repository.UserRepository;
import internship.project.election.service.impl.auth.util.AdminPasswordHashing;
import internship.project.election.service.impl.auth.util.UserPasswordHashing;
import internship.project.election.service.impl.domain.specification.UserSpecification;
import internship.project.election.service.spec.auth.util.AbstractPasswordHashing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

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

    public PagedModel<User> getAllUsers(String filter, Integer userType, Pageable pageable) {
        Specification<User> spec = null;
        boolean hasCondition = false;
        if (filter != null && !filter.isEmpty()) {
            spec = UserSpecification.getUsersByFilter(filter);
            hasCondition = true;
        }
        if (userType != null) {
            if (hasCondition) {
                spec = spec.and(UserSpecification.getUsersByType(userType));
            } else {
                spec = UserSpecification.getUsersByType(userType);
                hasCondition = true;
            }
        }
        return hasCondition ? new PagedModel<>(this.repository.findAll(spec, pageable))
                : new PagedModel<>(
                        this.repository.findAll(pageable));
    }

    public User getUserById(int id) {
        return this.repository.findById(id).orElse(null);
    }

    public User getUserByIdentifier(String identifier) {
        return this.repository.findByIdentifier(identifier).orElse(null);
    }

    public void deleteUser(Integer id) {
        this.repository.deleteById(id);
    }

    public User createNewUser(NewUserRequest request) {
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
        return this.repository.save(newUser);
    }

    public User updateUser(UpdateUserRequest updateRequest) {
        if (updateRequest.getId() == null) {
            throw new IllegalArgumentException("User id is required");
        }
        Optional<User> existingUser = this.repository.findById(updateRequest.getId());
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        Role role = new Role();
        role.setId(updateRequest.getRoleId());
        User user = new User(
                updateRequest.getIdentifier(), role, updateRequest.getName(), updateRequest.getFirstName(),
                updateRequest.getContact(), updateRequest.getPassword(), null);
        user.setId(updateRequest.getId());
        return this.repository.save(user);
    }

    @Transactional
    public void assignOperators() {
        this.entityManager.createNativeQuery("CALL assign_operators_to_polling_stations()").executeUpdate();
    }
}
