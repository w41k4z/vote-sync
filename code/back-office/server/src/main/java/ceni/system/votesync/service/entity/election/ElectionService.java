package ceni.system.votesync.service.entity.election;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.config.Status;
import ceni.system.votesync.dto.request.election.ConfigureElectionRequest;
import ceni.system.votesync.dto.request.election.UpdateElectionRequest;
import ceni.system.votesync.exception.ElectionNotFoundException;
import ceni.system.votesync.exception.ElectionTypeNotFoundException;
import ceni.system.votesync.exception.ImpossibleOperationException;
import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.model.entity.election.ElectionType;
import ceni.system.votesync.model.view.election.VElection;
import ceni.system.votesync.repository.entity.election.ElectionRepository;
import ceni.system.votesync.repository.view.election.VElectionRepository;

@Service
public class ElectionService {

    private ElectionRepository repository;
    private VElectionRepository vrepository;
    private ElectionTypeService electionTypeService;

    public ElectionService(ElectionRepository repository,
            VElectionRepository vrepository, ElectionTypeService electionTypeService) {
        this.repository = repository;
        this.vrepository = vrepository;
        this.electionTypeService = electionTypeService;
    }

    public Election configureElection(ConfigureElectionRequest request) {
        Election election = new Election();
        ElectionType electionType = this.electionTypeService.getElectionTypeById(request.getElectionTypeId())
                .orElseThrow(
                        () -> new ElectionTypeNotFoundException(
                                "Election type not found. Id: " + request.getElectionTypeId()));
        election.setType(electionType);
        election.setName(request.getName());
        election.setStartDate(Date.valueOf(request.getStartDate()));
        election.setStatus(Status.PENDING);
        return this.repository.save(election);
    }

    public Election updateElection(UpdateElectionRequest request) {
        if (request.getId() == null) {
            throw new ElectionNotFoundException(
                    "Failed to update the election because the election id null. It is required and must be provided");
        }
        Election existingElection = this.repository.findById(request.getId()).orElseThrow(
                () -> new ElectionNotFoundException("Election not found. Id: " + request.getId()));
        if (existingElection.getStatus() == Status.CLOSED) {
            throw new ImpossibleOperationException("Election is already closed and can not be updated");
        }
        existingElection.setName(request.getName());
        existingElection.setStartDate(Date.valueOf(request.getStartDate()));
        return this.repository.save(existingElection);
    }

    public void deleteElection(Integer electionId) {
        Election existingElection = this.repository.findById(electionId).orElseThrow(
                () -> new ElectionNotFoundException("Election not found. Id: " + electionId));
        if (existingElection.getStatus() == Status.CLOSED) {
            throw new ImpossibleOperationException("Election is already closed and can not be updated");
        }
        this.repository.deleteById(electionId);
    }

    public Optional<Election> getElection(Integer electionId) {
        return this.repository.findById(electionId);
    }

    public List<VElection> getCurrentElections() {
        Specification<VElection> spec = ElectionSpecification.currentElection();
        return this.vrepository.findAll(spec);
    }

    public PagedModel<Election> getElectionsHistory(Pageable pageable) {
        Specification<Election> spec = ElectionSpecification.closedElections();
        return new PagedModel<>(this.repository.findAll(spec, pageable));
    }
}
