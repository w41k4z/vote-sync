package internship.project.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.model.view.VUserStat;

public interface UserStatRepository extends JpaRepository<VUserStat, Integer> {

}
