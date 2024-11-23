package ceni.system.votesync.service.entity.voter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.config.Status;
import ceni.system.votesync.dto.request.RegisterVoterRequest;
import ceni.system.votesync.model.entity.VoterRegistration;
import ceni.system.votesync.model.entity.alert.AlertType;
import ceni.system.votesync.model.view.VRegisteredVoter;
import ceni.system.votesync.repository.entity.VoterRegistrationRepository;
import ceni.system.votesync.repository.view.VRegisteredVoterRepository;
import ceni.system.votesync.service.entity.alert.AlertService;

@Service
public class VoterService {

    public final static Time LIMIT_REGISTRATION = Time.valueOf("17:00:00");

    private VRegisteredVoterRepository registeredVoterRepository;
    private VoterRegistrationRepository voterRegistrationRepository;
    private AlertService alertService;

    public VoterService(VRegisteredVoterRepository registeredVoterRepository,
            VoterRegistrationRepository voterRegistrationRepository, AlertService alertService) {
        this.registeredVoterRepository = registeredVoterRepository;
        this.voterRegistrationRepository = voterRegistrationRepository;
        this.alertService = alertService;
    }

    public List<VRegisteredVoter> getRecordedVoters(Integer electionId, Integer pollingStationId) {
        return this.registeredVoterRepository.findByElectionIdAndPollingStationId(electionId, pollingStationId);
    }

    @Transactional
    public void registerVoters(RegisterVoterRequest[] registration) {
        List<VoterRegistration> voters = new ArrayList<>();
        int suspectRegistration = 0;
        for (RegisterVoterRequest request : registration) {
            String registrationTimeString = request.getRegistrationDate().getHour() + ":"
                    + request.getRegistrationDate().getMinute() + ":" + request.getRegistrationDate().getSecond();
            Time registrationTime = Time.valueOf(registrationTimeString);
            if (registrationTime.after(LIMIT_REGISTRATION)) {
                suspectRegistration++;
            }
            VoterRegistration voter = this.voterRegistrationRepository
                    .findByElectionIdAndVoterId(request.getElectionId(), request.getId());
            voter.setHasVoted(Status.CLOSED);
            voter.setRegistrationDate(Timestamp.valueOf(request.getRegistrationDate()));
            voters.add(voter);
        }
        this.voterRegistrationRepository.saveAll(voters);
        if (suspectRegistration > 0) {
            String message = suspectRegistration > 1 ? "Des enregistrements suspects ont été détecté."
                    : "Enregistrement suspect détecté";
            try {
                this.alertService.saveAlert(AlertType.SUSPECT_REGISTRATION, registration[0].getElectionId(),
                        registration[0].getPollingStationId(), Date.valueOf(LocalDate.now()), message, Status.PENDING);
            } catch (Exception e) {
                // Ignore duplicated alert
            }
        }
    }
}