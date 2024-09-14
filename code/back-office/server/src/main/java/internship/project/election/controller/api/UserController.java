package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.config.Pagination;
import internship.project.election.dto.ApiResponse;
import internship.project.election.service.impl.domain.UserService;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> allUsers(@RequestParam(defaultValue = Pagination.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Pagination.DEFAULT_SIZE) int size) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("users", this.userService.getAllUsers(page, size));
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }
}
