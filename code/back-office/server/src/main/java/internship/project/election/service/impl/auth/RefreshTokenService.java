package internship.project.election.service.impl.auth;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import internship.project.election.domain.RefreshToken;
import internship.project.election.repository.RefreshTokenRepository;
import internship.project.election.service.spec.AbstractJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class RefreshTokenService extends AbstractJwtService<String> {

    @Value("${refresh.token.expiration.time}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;

    private RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    private boolean isValid(String token) {
        try {
            boolean isValid = super.validateToken(token);
            if (!isValid) {
                return false;
            }
        } catch (ExpiredJwtException e) {
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
    public String generateToken(String userIdentifier) {
        return Jwts
                .builder()
                .setSubject(userIdentifier)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        return this.isValid(token);
    }

    public void store(String token) {
        String userIdentifier = this.getSubject(token);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserIdentifier(userIdentifier);
        refreshToken.setToken(token);
        this.repository.save(refreshToken);
    }

    public void invalidate(String token) {
        String userIdentifier = this.getSubject(token);
        this.repository.deleteById(userIdentifier);
    }
}
