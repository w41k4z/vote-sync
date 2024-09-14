package internship.project.election.service.impl.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import internship.project.election.model.Role;
import internship.project.election.model.User;
import internship.project.election.service.impl.domain.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
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
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                true,
                true,
                true,
                true,
                this.getGrantedAuthority(user.getRole()));
    }
}
