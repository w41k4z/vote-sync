package internship.project.election.repository.view;

import java.util.List;

import internship.project.election.model.view.VRegisteredVoter;
import internship.project.election.repository.EntityRepository;

public interface VRegisteredVoterRepository extends EntityRepository<VRegisteredVoter, Integer> {

    List<VRegisteredVoter> findByElectionIdAndPollingStationId(Integer electionId, Integer pollingStationId);
}
