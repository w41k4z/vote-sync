package internship.project.election.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.dto.ApiResponse;
import internship.project.election.dto.request.AuthenticationRequest;
import internship.project.election.dto.request.LogOutRequest;
import internship.project.election.dto.request.NewAccessTokenRequest;
import internship.project.election.service.impl.RefreshTokenService;
import internship.project.election.service.impl.auth.AppAuthService;
import jakarta.validation.Valid;

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
    public ResponseEntity<ApiResponse> signIn(@Valid @RequestBody AuthenticationRequest authRequest) {
        ApiResponse response = new ApiResponse(
                this.authService.logIn(authRequest.getIdentifier(), authRequest.getPassword()),
                "User logged in successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse> signOut(@RequestBody LogOutRequest request) {
        this.authService.logOut(request.getRefreshToken());
        return ResponseEntity.ok(new ApiResponse(null, "User logged out successfully"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody NewAccessTokenRequest request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            String newAccessToken = this.refreshTokenService.refreshedAccessToken(request.getRefreshToken());
            response.put("newAccessToken", newAccessToken);
            return ResponseEntity.ok(new ApiResponse(response, "Access token refreshed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Invalid refresh token"));
        }
    }

}
