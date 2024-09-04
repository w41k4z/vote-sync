package internship.project.election.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);
}
