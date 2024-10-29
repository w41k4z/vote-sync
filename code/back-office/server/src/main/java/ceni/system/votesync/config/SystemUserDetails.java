package ceni.system.votesync.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

public class SystemUserDetails implements UserDetails {

    @Getter
    private String name;
    @Getter
    private String firstName;
    private String identifier;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public SystemUserDetails(String name, String firstName, String identifier, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.firstName = firstName;
        this.identifier = identifier;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.identifier;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
