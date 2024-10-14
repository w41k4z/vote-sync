package ceni.system.votesync.service.impl.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ceni.system.votesync.config.Authority;
import ceni.system.votesync.model.Role;
import ceni.system.votesync.model.User;
import ceni.system.votesync.service.impl.AppJwtService;
import ceni.system.votesync.service.impl.RefreshTokenService;
import ceni.system.votesync.service.spec.auth.AuthService;

@Service
public class AppAuthService implements AuthService<Map<String, String>> {

    private AuthenticationManager authenticationManager;

    private RefreshTokenService refreshTokenService;

    private AppJwtService jwtService;

    public AppAuthService(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService,
            AppJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @Override
    public Map<String, String> logIn(String username, String password) {
        Map<String, String> response = new HashMap<>();
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = new User();
        Role userRole = new Role();
        userRole.setName(userDetails.getAuthorities().iterator().next().getAuthority());
        user.setIdentifier(userDetails.getUsername());
        user.setRole(userRole);
        Long bonusTime = 0L;
        if (userRole.getName().equals(Authority.ADMIN) || userRole.getName().equals(Authority.OPERATOR)) {
            String refreshToken = this.refreshTokenService.generateToken(user.getIdentifier(), bonusTime);
            response.put("refreshToken", refreshToken);
            this.refreshTokenService.store(refreshToken);
        } else {
            bonusTime = Authority.MOBILE_USER_BONUS_TIME;
        }
        String accessToken = this.jwtService.generateToken(user, bonusTime);
        response.put("accessToken", accessToken);
        return response;
    }

    @Override
    public void logOut(String refreshToken) {
        this.refreshTokenService.invalidate(refreshToken);
    }
}
