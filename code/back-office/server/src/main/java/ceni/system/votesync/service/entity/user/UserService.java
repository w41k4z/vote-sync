package ceni.system.votesync.service.entity.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ceni.system.votesync.dto.request.user.NewUserRequest;
import ceni.system.votesync.dto.request.user.UpdateUserRequest;
import ceni.system.votesync.model.entity.Role;
import ceni.system.votesync.model.entity.User;
import ceni.system.votesync.repository.entity.UserRepository;
import ceni.system.votesync.service.spec.auth.util.AbstractPasswordHashing;
import ceni.system.votesync.service.PasswordHashingService;
import ceni.system.votesync.service.entity.role.RoleService;
import ceni.system.votesync.exception.UserNotFoundException;
import ceni.system.votesync.exception.RoleNotFoundException;

@Service
public class UserService {

    private UserFactory userFactory;

    private UserRepository repository;

    private RoleService roleService;

    private PasswordHashingService passwordHashingService;

    public UserService(UserFactory userFactory, UserRepository repository, RoleService roleService,
            PasswordHashingService passwordHashingService) {
        this.userFactory = userFactory;
        this.repository = repository;
        this.roleService = roleService;
        this.passwordHashingService = passwordHashingService;
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

    public User registerNewUser(NewUserRequest request) {
        User newUser = this.userFactory.createUser(request);
        return this.repository.save(newUser);
    }

    public User updateUser(UpdateUserRequest updateRequest) {
        if (updateRequest.getId() == null) {
            throw new UserNotFoundException(
                    "Failed to update the user because the user id null. It is required and must be provided");
        }
        User existingUser = this.repository.findById(updateRequest.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found. Id: " + updateRequest.getId()));
        Role role = new Role();
        role.setId(updateRequest.getRoleId());
        existingUser.setIdentifier(updateRequest.getIdentifier());
        existingUser.setRole(role);
        existingUser.setName(updateRequest.getName());
        existingUser.setFirstName(updateRequest.getFirstName());
        existingUser.setContact(updateRequest.getContact());
        existingUser.setPassword(updateRequest.getPassword());
        return this.repository.save(existingUser);
    }

    public void importUsers(MultipartFile file, Integer roleId) {
        Role role = this.roleService.getRoleById(roleId);
        if (role == null) {
            throw new RoleNotFoundException("Role not found");
        }
        AbstractPasswordHashing passwordHashing = this.passwordHashingService.selectPasswordHashingStrategy(role);
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
                int affectedRow = this.repository.updateByIdentifier(role.getId(), user.getName(), user.getFirstName(),
                        user.getContact(), user.getPassword(), user.getIdentifier());
                if (affectedRow == 0) {
                    try {
                        this.repository.save(user);
                    } catch (Exception e) {
                        errors.put(line, e.getMessage());
                    }
                }
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error reading imported file. Source: " + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error importing users. Details: " + e.getMessage());
        }
    }

    public void assignOperators() {
        this.repository.assignOperators();
    }
}
