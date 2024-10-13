package ceni.system.votesync.repository;

import java.util.Optional;

import ceni.system.votesync.model.RefreshToken;

public interface RefreshTokenRepository extends EntityRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
