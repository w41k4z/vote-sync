package ceni.system.votesync.service.entity.election;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.config.State;
import ceni.system.votesync.dto.request.election.ConfigureElectionRequest;
import ceni.system.votesync.dto.request.election.UpdateElectionRequest;
import ceni.system.votesync.model.entity.Election;
import ceni.system.votesync.model.entity.ElectionType;
import ceni.system.votesync.repository.entity.ElectionRepository;
import ceni.system.votesync.exception.ElectionNotFoundException;
import ceni.system.votesync.exception.ImpossibleOperationException;

@Service
public class ElectionService {

    private ElectionRepository repository;

    public ElectionService(ElectionRepository repository) {
        this.repository = repository;
    }

    public Election configureElection(ConfigureElectionRequest request) {
        Election election = new Election();
        election.setName(request.getName());
        election.setStartDate(Date.valueOf(request.getStartDate()));
        election.setState(State.PENDING);
        return this.repository.save(election);
    }

    public Election updateElection(UpdateElectionRequest request) {
        if (request.getId() == null) {
            throw new ElectionNotFoundException(
                    "Failed to update the election because the election id null. It is required and must be provided");
        }
        Election existingElection = this.repository.findById(request.getId()).orElseThrow(
                () -> new ElectionNotFoundException("Election not found. Id: " + request.getId()));
        if (existingElection.getState() == State.CLOSED) {
            throw new ImpossibleOperationException("Election is already closed and can not be updated");
        }
        ElectionType type = new ElectionType();
        type.setId(request.getElectionTypeId());
        existingElection.setName(request.getName());
        existingElection.setType(type);
        existingElection.setStartDate(Date.valueOf(request.getStartDate()));
        return this.repository.save(existingElection);
    }

    public void deleteElection(Integer electionId) {
        Election existingElection = this.repository.findById(electionId).orElseThrow(
                () -> new ElectionNotFoundException("Election not found. Id: " + electionId));
        if (existingElection.getState() == State.CLOSED) {
            throw new ImpossibleOperationException("Election is already closed and can not be updated");
        }
        this.repository.deleteById(electionId);
    }

    public Optional<Election> getElection(Integer electionId) {
        return this.repository.findById(electionId);
    }

    public List<Election> getCurrentElections() {
        Specification<Election> spec = ElectionSpecification.currentElection();
        return this.repository.findAll(spec);
    }

    public PagedModel<Election> getElectionsHistory(Pageable pageable) {
        Specification<Election> spec = ElectionSpecification.closedElections();
        return new PagedModel<>(this.repository.findAll(spec, pageable));
    }
}
