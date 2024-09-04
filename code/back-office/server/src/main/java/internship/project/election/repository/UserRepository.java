package internship.project.election.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByIdentifier(String identifier);
}
