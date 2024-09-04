package internship.project.election.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.dto.request.AuthenticationRequest;
import internship.project.election.service.impl.auth.AppAuthService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private AppAuthService authService;

    public AuthenticationController(AppAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse> signIn(@RequestBody AuthenticationRequest authRequest) {
        ApiResponse response = new ApiResponse(
                this.authService.logIn(authRequest.getIdentifier(), authRequest.getPassword()),
                "User logged in successfully");
        return ResponseEntity.ok(response);
    }
}
