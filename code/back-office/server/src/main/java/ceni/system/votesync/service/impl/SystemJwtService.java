package ceni.system.votesync.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.Role;
import ceni.system.votesync.model.entity.User;
import ceni.system.votesync.service.spec.AbstractJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SystemJwtService extends AbstractJwtService<User> {

    @Override
    public String generateToken(User user, Long BONUS_TIME) {
        Map<String, Object> claims = new HashMap<>();
        String authority = user.getRole().getName();
        claims.put("userInformation", user.getName() + " " + user.getFirstName());
        claims.put("authority", authority);
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getIdentifier())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME + BONUS_TIME))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
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
}
