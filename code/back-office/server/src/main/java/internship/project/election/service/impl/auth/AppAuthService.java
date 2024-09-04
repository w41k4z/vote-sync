package internship.project.election.service.impl.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import internship.project.election.domain.Role;
import internship.project.election.domain.User;
import internship.project.election.service.spec.AuthService;

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
        String accessToken = this.jwtService.generateToken(user);
        String refreshToken = this.refreshTokenService.generateToken(user.getIdentifier());
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        this.refreshTokenService.store(refreshToken);
        return response;
    }

    @Override
    public void logOut(String refreshToken) {
        this.refreshTokenService.invalidate(refreshToken);
    }
}
