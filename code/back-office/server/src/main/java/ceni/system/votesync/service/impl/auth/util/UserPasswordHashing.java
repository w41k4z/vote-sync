package ceni.system.votesync.service.impl.auth.util;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ceni.system.votesync.service.spec.auth.util.AbstractPasswordHashing;

@Service
public class UserPasswordHashing extends AbstractPasswordHashing {

    private PasswordEncoder encoder;

    @SuppressWarnings("deprecation")
    public UserPasswordHashing() {
        this.encoder = NoOpPasswordEncoder.getInstance();
    }

    @Override
    public String hash(String password) {
        return "{noop}" + this.encoder.encode(password);
    }
}
