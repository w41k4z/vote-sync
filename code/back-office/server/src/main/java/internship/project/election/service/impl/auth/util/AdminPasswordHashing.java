package internship.project.election.service.impl.auth.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import internship.project.election.service.spec.util.AbstractPasswordHashing;

public class AdminPasswordHashing extends AbstractPasswordHashing {

    private PasswordEncoder encoder;

    public AdminPasswordHashing(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String getIdForEncode() {
        return "bcrypt";
    }

    @Override
    protected String hash(String password) {
        return this.encoder.encode(password);
    }

    @Override
    public String getFormattedPassword(String password) {
        // The DelegatingPasswordEncoder already formats the password
        return this.hash(password);
    }
}
