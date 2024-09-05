package internship.project.election.repository;

import java.util.Optional;

import internship.project.election.domain.Role;

public interface RoleRepository extends EntityRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
