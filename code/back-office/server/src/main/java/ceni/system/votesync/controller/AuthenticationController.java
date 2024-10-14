package ceni.system.votesync.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.dto.request.AuthenticationRequest;
import ceni.system.votesync.dto.request.LogOutRequest;
import ceni.system.votesync.dto.request.NewAccessTokenRequest;
import ceni.system.votesync.service.impl.RefreshTokenService;
import ceni.system.votesync.service.impl.auth.AppAuthService;
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

    // Mobile app wont have a sign-out feature from the server
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
