package internship.project.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
