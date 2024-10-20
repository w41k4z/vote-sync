package ceni.system.votesync.service.entity.role;

import java.util.List;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.Role;
import ceni.system.votesync.repository.entity.RoleRepository;

@Service
public class RoleService {

    private RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getAllRoles() {
        return this.repository.findAll();
    }

    public Role getRoleById(int id) {
        return this.repository.findById(id).orElse(null);
    }

    public Role getRoleByName(String name) {
        return this.repository.findByName(name).orElse(null);
    }
}
