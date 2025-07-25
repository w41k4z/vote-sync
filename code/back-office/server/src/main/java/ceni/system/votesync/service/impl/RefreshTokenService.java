package ceni.system.votesync.service.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.RefreshToken;
import ceni.system.votesync.model.entity.User;
import ceni.system.votesync.repository.entity.RefreshTokenRepository;
import ceni.system.votesync.service.entity.user.UserService;
import ceni.system.votesync.service.spec.AbstractJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class RefreshTokenService extends AbstractJwtService<String> {

    @Value("${refresh.token.expiration.time}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;

    private RefreshTokenRepository repository;

    private SystemJwtService jwtService;

    private UserService userService;

    public RefreshTokenService(RefreshTokenRepository repository, SystemJwtService jwtService,
            UserService userService) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    private boolean isValid(String token) {
        try {
            boolean isValid = super.validateToken(token);
            if (!isValid) {
                return false;
            }
        } catch (ExpiredJwtException e) {
            this.invalidate(token);
            // Expiration handling is specific to the auth token
            return false;
        }

        // Invalidated token check
        if (this.repository.findByToken(token).isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public String generateToken(String userIdentifier, Long BONUS_TIME) {
        return Jwts
                .builder()
                .setSubject(userIdentifier)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME + BONUS_TIME))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        return this.isValid(token);
    }

    public String refreshedAccessToken(String refreshToken) {
        if (this.validateToken(refreshToken)) {
            String userIdentifier = this.getSubject(refreshToken);
            User user = this.userService.getUserByIdentifier(userIdentifier);
            return this.jwtService.generateToken(user, 0L);
        }
        throw new IllegalArgumentException("Invalid refresh token");
    }

    public void store(String token) {
        String userIdentifier = this.getSubject(token);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserIdentifier(userIdentifier);
        refreshToken.setToken(token);
        this.repository.save(refreshToken);
    }

    public void invalidate(String token) {
        RefreshToken refreshToken = this.repository.findByToken(token).get();
        if (refreshToken != null) {
            this.repository.delete(refreshToken);
        }
    }
}
