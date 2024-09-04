package internship.project.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
