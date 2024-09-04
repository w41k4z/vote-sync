package internship.project.election.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.dto.request.AuthenticationRequest;
import internship.project.election.service.impl.auth.AppAuthService;
import internship.project.election.service.impl.auth.RefreshTokenService;
import jakarta.transaction.Transactional;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private AppAuthService authService;
    private RefreshTokenService refreshTokenService;

    public AuthenticationController(AppAuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse> signIn(@RequestBody AuthenticationRequest authRequest) {
        ApiResponse response = new ApiResponse(
                this.authService.logIn(authRequest.getIdentifier(), authRequest.getPassword()),
                "User logged in successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    @Transactional
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody String refreshToken) {
        HashMap<String, String> response = new HashMap<>();
        if (this.refreshTokenService.validateToken(refreshToken)) {
            String userIdentifier = this.refreshTokenService.getSubject(refreshToken);
            String newRefreshToken = this.refreshTokenService.generateToken(userIdentifier);
            this.refreshTokenService.invalidate(refreshToken);
            this.refreshTokenService.store(newRefreshToken);
            response.put("accessToken", newRefreshToken);
            return ResponseEntity.ok(new ApiResponse(response, "Token refreshed successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(null, "Invalid refresh token"));
    }

}
