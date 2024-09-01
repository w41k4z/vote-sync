package internship.project.election.service.impl;

import java.security.Key;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import internship.project.election.domain.Role;
import internship.project.election.domain.User;
import internship.project.election.service.spec.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements ITokenService<User> {

    @Value("${jwt.private.key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration.time}")
    private Long EXPIRATION_TIME;

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        String authority = user.getRole().getName();
        claims.put("authority", authority);
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getIdentifier())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(this.getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User extractUserFromToken(String token) {
        Claims claims = this.extractAllClaims(token);
        User user = new User();
        Role userRole = new Role();
        String userIdentifier = claims.getSubject();
        String userAuthority = (String) claims.get("authority");
        user.setIdentifier(userIdentifier);
        userRole.setName(userAuthority);
        user.setRole(userRole);
        return user;
    }

    public UserDetails getUserDetailsFromUser(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getIdentifier(), "",
                grantedAuthorities);
    }
}
