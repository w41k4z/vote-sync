package internship.project.election.service.impl.auth.util;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import internship.project.election.service.spec.util.AbstractPasswordHashing;

public class UserPasswordHashing extends AbstractPasswordHashing {

    private PasswordEncoder encoder;

    @SuppressWarnings("deprecation")
    public UserPasswordHashing() {
        this.encoder = NoOpPasswordEncoder.getInstance();
    }

    @Override
    public String getIdForEncode() {
        return "noop";
    }

    @Override
    public String hash(String password) {
        return this.encoder.encode(password);
    }
}
