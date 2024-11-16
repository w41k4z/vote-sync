package ceni.system.votesync.service.spec.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ceni.system.votesync.config.SystemUserDetails;

public interface AuthService<RESP> {

    public RESP logIn(String username, String password);

    public void logOut(String token);

    public static SystemUserDetails getActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SystemUserDetails) authentication.getPrincipal();
    }
}