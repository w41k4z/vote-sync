package ceni.system.votesync.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import ceni.system.votesync.dto.ApiResponse;
import ceni.system.votesync.model.User;
import ceni.system.votesync.service.impl.AppJwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private AppJwtService jwtService;

    public JwtFilter(AppJwtService jwtService) {
        this.jwtService = jwtService;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ApiResponse content = new ApiResponse(null, "Unauthorized");
        response.getWriter().write(new Gson().toJson(content));
    }

    private void sendRefreshTokenRequiredResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        HashMap<String, String> payload = new HashMap<>();
        payload.put("refreshTokenRequired", "true");
        ApiResponse content = new ApiResponse(payload, "Refresh token required");
        response.getWriter().write(new Gson().toJson(content));
    }

    private UserDetails getUserDetailsFromUser(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getIdentifier(), "",
                grantedAuthorities);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.split(" ")[1].trim();
        try {
            boolean isValidToken = jwtService.validateToken(token);
            if (!isValidToken) {
                this.sendUnauthorizedResponse(response);
                return;
            }
        } catch (ExpiredJwtException e) {
            this.sendRefreshTokenRequiredResponse(response);
            return;
        }

        User user = jwtService.extractUserFromToken(token);
        UserDetails userDetails = this.getUserDetailsFromUser(user);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}

// eyJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdHkiOiJNQU5BR0VSIiwic3ViIjoibWFuYWdlciIsImlhdCI6MTcyNTg3ODE2MiwiZXhwIjoxNzI1ODc4NDYyfQ.qdIKK3W57ZENOz4hNB8ji6JHSxsQCY0sGOrluebNxK0