package ceni.system.votesync.repository.view.election;

import java.util.Optional;

import ceni.system.votesync.model.view.election.PollingStationVotersStat;
import ceni.system.votesync.repository.base.EntityRepository;

public interface PollingStationVotersStatRepository extends EntityRepository<PollingStationVotersStat, Integer> {

    Optional<PollingStationVotersStat> findByElectionIdAndPollingStationId(Integer electionId,
            Integer pollingStationId);
}