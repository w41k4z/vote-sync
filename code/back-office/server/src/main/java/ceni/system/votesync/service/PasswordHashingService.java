package ceni.system.votesync.service;

import org.springframework.stereotype.Service;

import ceni.system.votesync.config.Authority;
import ceni.system.votesync.model.entity.Role;
import ceni.system.votesync.service.spec.auth.util.AbstractPasswordHashing;
import ceni.system.votesync.service.impl.auth.util.AdminPasswordHashing;
import ceni.system.votesync.service.impl.auth.util.UserPasswordHashing;

@Service
public class PasswordHashingService {

    private AdminPasswordHashing adminPasswordHashing;
    private UserPasswordHashing userPasswordHashing;

    public PasswordHashingService(AdminPasswordHashing adminPasswordHashing, UserPasswordHashing userPasswordHashing) {
        this.adminPasswordHashing = adminPasswordHashing;
        this.userPasswordHashing = userPasswordHashing;
    }

    public AbstractPasswordHashing selectPasswordHashingStrategy(Role role) {
        if (role.getName().equals(Authority.ADMIN.toString())) {
            return this.adminPasswordHashing;
        } else {
            return this.userPasswordHashing;
        }
    }
}
