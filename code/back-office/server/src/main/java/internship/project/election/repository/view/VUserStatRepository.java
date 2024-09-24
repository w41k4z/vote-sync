package internship.project.election.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.model.view.VUserStat;

public interface VUserStatRepository extends JpaRepository<VUserStat, Integer> {

}
