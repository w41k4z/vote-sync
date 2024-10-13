package ceni.system.votesync.repository;

import java.util.Optional;

import ceni.system.votesync.model.User;

public interface UserRepository extends EntityRepository<User, Integer> {

    Optional<User> findByIdentifier(String identifier);
}
