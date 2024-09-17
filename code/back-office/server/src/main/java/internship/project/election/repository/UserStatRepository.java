package internship.project.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.model.VUserStat;

public interface UserStatRepository extends JpaRepository<VUserStat, Integer> {

}
