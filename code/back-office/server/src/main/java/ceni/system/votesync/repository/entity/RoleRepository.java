package ceni.system.votesync.repository.entity;

import java.util.Optional;

import ceni.system.votesync.model.entity.Role;
import ceni.system.votesync.repository.base.EntityRepository;

public interface RoleRepository extends EntityRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
