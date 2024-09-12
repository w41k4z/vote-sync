package internship.project.election.controller.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse> allUsers() {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("users", this.userService.getAllUsers());
        return ResponseEntity.ok(new ApiResponse(payload, null));
    }
}
