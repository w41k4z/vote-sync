package ceni.system.votesync.service.domain;

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
import ceni.system.votesync.model.Election;
import ceni.system.votesync.model.ElectionType;
import ceni.system.votesync.repository.ElectionRepository;
import ceni.system.votesync.service.domain.specification.ElectionSpecification;

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
            throw new IllegalArgumentException("Election id is required");
        }
        Optional<Election> existingElection = this.repository.findById(request.getId());
        if (existingElection.isEmpty()) {
            throw new IllegalArgumentException("Election not found");
        }
        if (existingElection.get().getState() == State.CLOSED) {
            throw new IllegalArgumentException("Election is already closed and can not be updated");
        }
        ElectionType type = new ElectionType();
        type.setId(request.getElectionTypeId());
        Election election = new Election(
                type, request.getName(), Date.valueOf(request.getStartDate()), existingElection.get().getEndDate(),
                existingElection.get().getState(), 0);
        election.setId(request.getId());
        return this.repository.save(election);
    }

    public void deleteElection(Integer electionId) {
        Optional<Election> existingElection = this.repository.findById(electionId);
        if (existingElection.isEmpty()) {
            throw new IllegalArgumentException("Election not found");
        }
        if (existingElection.get().getState() == State.CLOSED) {
            throw new IllegalArgumentException("Election is already closed and can not be deleted");
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
