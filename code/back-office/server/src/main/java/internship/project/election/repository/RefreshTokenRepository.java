package internship.project.election.repository;

import java.util.Optional;

import internship.project.election.model.RefreshToken;

public interface RefreshTokenRepository extends EntityRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
