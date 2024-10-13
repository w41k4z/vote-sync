package ceni.system.votesync.service.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.function.RegisteredCandidate;
import ceni.system.votesync.repository.function.RegisteredCandidateRepository;

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
