package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.Role;
import internship.project.election.repository.RoleRepository;

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
