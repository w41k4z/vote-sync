package ceni.system.votesync.service.impl.auth.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ceni.system.votesync.service.spec.auth.util.AbstractPasswordHashing;

@Service
public class AdminPasswordHashing extends AbstractPasswordHashing {

    private PasswordEncoder encoder;

    public AdminPasswordHashing(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String hash(String password) {
        return this.encoder.encode(password);
    }
}
