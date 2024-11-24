package ceni.system.votesync.repository.entity.election;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.repository.base.EntityRepository;

public interface ElectionRepository extends EntityRepository<Election, Integer> {

    @Transactional
    @Procedure(procedureName = "close_election")
    void closeElectionById(Integer electionId);
    
    @Procedure(procedureName = "data_simulation")
    void simulateElectionData(Integer electionId);
}
