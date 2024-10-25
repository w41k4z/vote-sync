package ceni.system.votesync.service.entity.voter;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.config.State;
import ceni.system.votesync.dto.request.RegisterVoterRequest;
import ceni.system.votesync.model.entity.VoterRegistration;
import ceni.system.votesync.model.view.VRegisteredVoter;
import ceni.system.votesync.repository.entity.VoterRegistrationRepository;
import ceni.system.votesync.repository.view.VRegisteredVoterRepository;

@Service
public class VoterService {

    private VRegisteredVoterRepository registeredVoterRepository;
    private VoterRegistrationRepository voterRegistrationRepository;

    public VoterService(VRegisteredVoterRepository registeredVoterRepository,
            VoterRegistrationRepository voterRegistrationRepository) {
        this.registeredVoterRepository = registeredVoterRepository;
        this.voterRegistrationRepository = voterRegistrationRepository;
    }

    public List<VRegisteredVoter> getRecordedVoters(Integer electionId, Integer pollingStationId) {
        return this.registeredVoterRepository.findByElectionIdAndPollingStationId(electionId, pollingStationId);
    }

    @Transactional
    public void registerVoters(RegisterVoterRequest[] registration) {
        List<VoterRegistration> voters = new ArrayList<>();
        for (RegisterVoterRequest request : registration) {
            VoterRegistration voter = this.voterRegistrationRepository
                    .findByElectionIdAndVoterId(request.getElectionId(), request.getId());
            voter.setHasVoted(State.CLOSED);
            voter.setRegistrationDate(Timestamp.valueOf(request.getRegistrationDate()));
            voters.add(voter);
        }
        this.voterRegistrationRepository.saveAll(voters);
    }
}