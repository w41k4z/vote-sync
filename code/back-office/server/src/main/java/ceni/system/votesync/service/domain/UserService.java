package ceni.system.votesync.service.domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ceni.system.votesync.config.Authority;
import ceni.system.votesync.dto.request.user.NewUserRequest;
import ceni.system.votesync.dto.request.user.UpdateUserRequest;
import ceni.system.votesync.model.Role;
import ceni.system.votesync.model.User;
import ceni.system.votesync.repository.UserRepository;
import ceni.system.votesync.service.domain.specification.UserSpecification;
import ceni.system.votesync.service.impl.auth.util.AdminPasswordHashing;
import ceni.system.votesync.service.impl.auth.util.UserPasswordHashing;
import ceni.system.votesync.service.spec.auth.util.AbstractPasswordHashing;
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
        Specification<User> spec = UserSpecification.filterUsers(filter, userType);
        return new PagedModel<>(this.repository.findAll(spec, pageable));
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
        User newUser = new User();
        AbstractPasswordHashing passwordHashing;
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

    public void importUsers(MultipartFile file, Integer roleId) {
        AbstractPasswordHashing passwordHashing;
        Role role = this.roleService.getRoleById(roleId);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        if (role.getName().equals(Authority.ADMIN.toString())) {
            passwordHashing = this.adminPasswordHashing;
        } else {
            passwordHashing = this.userPasswordHashing;
        }
        HashMap<Integer, String> errors = new HashMap<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int line = 0;
            while (rowIterator.hasNext()) {
                if (line == 0) {
                    rowIterator.next();
                    line++;
                    continue;
                }
                Row row = rowIterator.next();
                User user = new User();
                user.setName(row.getCell(0).getStringCellValue());
                user.setFirstName(row.getCell(1).getStringCellValue());
                user.setIdentifier(row.getCell(2).getStringCellValue());
                user.setContact(row.getCell(3).getStringCellValue());
                user.setPassword(passwordHashing.hash(row.getCell(4).getStringCellValue()));
                user.setRole(role);
                try {
                    this.repository.updateByIdentifier(role.getId(), user.getName(), user.getFirstName(),
                            user.getContact(), user.getPassword(), user.getIdentifier());
                } catch (Exception e) {
                    try {
                        this.repository.save(user);
                    } catch (Exception e2) {
                        errors.put(line, e2.getMessage());
                    }
                }
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error reading imported file. Source: " + file.getOriginalFilename());
        }
    }

    @Transactional
    public void assignOperators() {
        this.entityManager.createNativeQuery("CALL assign_operators_to_polling_stations()").executeUpdate();
    }
}
