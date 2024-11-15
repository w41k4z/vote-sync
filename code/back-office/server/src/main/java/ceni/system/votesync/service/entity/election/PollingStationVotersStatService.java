package ceni.system.votesync.service.entity.election;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.view.election.PollingStationVotersStat;
import ceni.system.votesync.repository.view.election.PollingStationVotersStatRepository;

@Service
public class PollingStationVotersStatService {

    private PollingStationVotersStatRepository repository;

    public PollingStationVotersStatService(PollingStationVotersStatRepository repository) {
        this.repository = repository;
    }

    public Optional<PollingStationVotersStat> getPollingStationVotersStatByElectionIdAndPollingStationId(
            Integer electionId, Integer pollingStationId) {
        return repository.findByElectionIdAndPollingStationId(electionId, pollingStationId);
    }
}
