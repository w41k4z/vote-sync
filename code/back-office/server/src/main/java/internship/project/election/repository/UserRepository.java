package internship.project.election.repository;

import java.util.Optional;

import internship.project.election.domain.User;

public interface UserRepository extends EntityRepository<User, Integer> {

    Optional<User> findByIdentifier(String identifier);
}
