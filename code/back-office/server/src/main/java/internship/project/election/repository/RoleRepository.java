package internship.project.election.repository;

import java.util.Optional;

import internship.project.election.model.Role;

public interface RoleRepository extends EntityRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
