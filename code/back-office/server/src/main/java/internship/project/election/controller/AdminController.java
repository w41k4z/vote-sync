package internship.project.election.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.dto.request.NewUserRequest;
import internship.project.election.service.impl.domain.UserService;

@RequestMapping("/admin")
@RestController
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse> createNewUser(@RequestBody NewUserRequest request) {
        this.userService.createNewUser(request);
        return new ResponseEntity<>(new ApiResponse(null, "New user created successfully"), HttpStatus.CREATED);
    }
}
