package ceni.system.votesync.repository;

import java.util.Optional;

import ceni.system.votesync.model.Role;

public interface RoleRepository extends EntityRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
