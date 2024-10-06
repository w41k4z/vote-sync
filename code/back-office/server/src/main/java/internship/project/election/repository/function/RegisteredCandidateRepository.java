package internship.project.election.repository.function;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import internship.project.election.model.function.RegisteredCandidate;
import internship.project.election.repository.EntityRepository;

public interface RegisteredCandidateRepository extends EntityRepository<RegisteredCandidate, Integer> {

    @Query(value = "SELECT * FROM get_registered_candidates(:electionId, :pollingStationId)", nativeQuery = true)
    List<RegisteredCandidate> findByElectionIdAndPollingStationId(@Param("electionId") Integer electionId,
            @Param("pollingStationId") Integer pollingStationId);
}
