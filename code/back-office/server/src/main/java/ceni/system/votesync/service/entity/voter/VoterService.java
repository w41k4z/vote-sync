package ceni.system.votesync.service.entity.voter;

import java.util.List;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.view.VRegisteredVoter;
import ceni.system.votesync.repository.view.VRegisteredVoterRepository;

@Service
public class VoterService {

    private VRegisteredVoterRepository registeredVoterRepository;

    public VoterService(VRegisteredVoterRepository registeredVoterRepository) {
        this.registeredVoterRepository = registeredVoterRepository;
    }

    public List<VRegisteredVoter> getRegisteredVoters(Integer electionId, Integer pollingStationId) {
        return this.registeredVoterRepository.findByElectionIdAndPollingStationId(electionId, pollingStationId);
    }
}
