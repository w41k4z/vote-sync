package internship.project.election.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.project.election.model.location.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {

}
