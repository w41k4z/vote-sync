package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.view.VRegisteredVoter;
import internship.project.election.repository.view.VRegisteredVoterRepository;

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
