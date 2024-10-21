package ceni.system.votesync.repository.entity;

import ceni.system.votesync.model.entity.VoterRegistration;
import ceni.system.votesync.repository.base.EntityRepository;

public interface VoterRegistrationRepository extends EntityRepository<VoterRegistration, Integer> {

    // electionId and voterId have unique constraint
    VoterRegistration findByElectionIdAndVoterId(Integer electionId, Integer voterId);
}
