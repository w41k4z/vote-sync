package ceni.system.votesync.service.spec.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService<RESP> {

    public RESP logIn(String username, String password);

    public void logOut(String token);

    public static UserDetails getActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }
}