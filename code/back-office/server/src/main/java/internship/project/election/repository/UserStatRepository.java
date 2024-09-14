package internship.project.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.model.UserStat;

public interface UserStatRepository extends JpaRepository<UserStat, Integer> {

}
