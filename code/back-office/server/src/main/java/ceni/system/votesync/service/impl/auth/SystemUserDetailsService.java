package ceni.system.votesync.service.impl.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ceni.system.votesync.config.SystemUserDetails;
import ceni.system.votesync.model.entity.Role;
import ceni.system.votesync.model.entity.User;
import ceni.system.votesync.service.entity.user.UserService;

@Service
public class SystemUserDetailsService implements UserDetailsService {

    private UserService userService;

    public SystemUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthority(
            Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getUserByIdentifier(username);
        if (user == null) {
            throw new UsernameNotFoundException("User: `" + username + "` not found");
        }
        return new SystemUserDetails(
                user.getName(),
                user.getFirstName(),
                username,
                user.getPassword(),
                this.getGrantedAuthority(user.getRole()));
    }
}
