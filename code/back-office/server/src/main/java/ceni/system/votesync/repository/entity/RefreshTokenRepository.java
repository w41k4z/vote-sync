package ceni.system.votesync.repository.entity;

import java.util.Optional;

import ceni.system.votesync.model.entity.RefreshToken;
import ceni.system.votesync.repository.base.EntityRepository;

public interface RefreshTokenRepository extends EntityRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
