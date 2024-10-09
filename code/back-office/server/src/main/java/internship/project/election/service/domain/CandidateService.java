package internship.project.election.service.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.function.RegisteredCandidate;
import internship.project.election.repository.function.RegisteredCandidateRepository;

@Service
public class CandidateService {

    private RegisteredCandidateRepository registeredCandidateRepository;

    public CandidateService(RegisteredCandidateRepository registeredCandidateRepository) {
        this.registeredCandidateRepository = registeredCandidateRepository;
    }

    public List<RegisteredCandidate> getRegisteredCandidates(Integer electionId, Integer pollingStationId) {
        return this.registeredCandidateRepository.findByElectionIdAndPollingStationId(electionId, pollingStationId);
    }
}
