package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.config.Pagination;
import internship.project.election.dto.ApiResponse;
import internship.project.election.dto.request.user.NewUserRequest;
import internship.project.election.dto.request.user.UpdateUserRequest;
import internship.project.election.service.impl.domain.UserService;
import internship.project.election.service.impl.domain.UserStatService;
import jakarta.validation.Valid;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private UserService userService;
    private UserStatService userStatService;

    public UserController(UserService userService, UserStatService userStatService) {
        this.userService = userService;
        this.userStatService = userStatService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> allUsers(
            @PageableDefault(value = Pagination.DEFAULT_SIZE, page = Pagination.DEFAULT_PAGE) Pageable pageable,
            @RequestParam(required = false) String filter, @RequestParam(required = false) Integer userTypeFilter) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("users", this.userService.getAllUsers(filter, userTypeFilter, pageable));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @GetMapping("/users-and-stats")
    public ResponseEntity<ApiResponse> allUsersAndStats(
            @PageableDefault(value = Pagination.DEFAULT_SIZE, page = Pagination.DEFAULT_PAGE) Pageable pageable,
            @RequestParam(required = false) String filter, @RequestParam(required = false) Integer userTypeFilter) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("users", this.userService.getAllUsers(filter, userTypeFilter, pageable));
        payload.put("stats", this.userStatService.getUserStats());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createNewUser(@Valid @RequestBody NewUserRequest request) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user", this.userService.createNewUser(request));
        return new ResponseEntity<>(new ApiResponse(payload, "User created"), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user", this.userService.updateUser(request));
        return ResponseEntity.ok(new ApiResponse(payload, "User updated"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser(@RequestParam(required = true) Integer userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse(null, "User deleted"));
    }
}
