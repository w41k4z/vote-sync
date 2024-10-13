package ceni.system.votesync.repository.view;

import java.util.List;

import ceni.system.votesync.model.view.VRegisteredVoter;
import ceni.system.votesync.repository.EntityRepository;

public interface VRegisteredVoterRepository extends EntityRepository<VRegisteredVoter, Integer> {

    List<VRegisteredVoter> findByElectionIdAndPollingStationId(Integer electionId, Integer pollingStationId);
}
