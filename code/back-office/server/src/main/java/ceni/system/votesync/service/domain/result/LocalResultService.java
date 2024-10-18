package ceni.system.votesync.service.domain.result;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.Election;
import ceni.system.votesync.model.result.FokontanyLocalElectionResult;
import ceni.system.votesync.model.result.MunicipalResult;
import ceni.system.votesync.model.result.PollingStationLocalElectionResult;
import ceni.system.votesync.repository.result.FokontanyLocalElectionResultRepository;
import ceni.system.votesync.repository.result.FokontanyResultRepository;
import ceni.system.votesync.repository.result.MunicipalResultRepository;
import ceni.system.votesync.repository.result.PollingStationLocalElectionResultRepository;
import ceni.system.votesync.repository.result.PollingStationResultRepository;
import ceni.system.votesync.service.domain.ElectionService;
import ceni.system.votesync.service.domain.specification.ElectoralResultSpecification;

@Service
public class LocalResultService {

    private PollingStationLocalElectionResultRepository pollingStationLocalElectionResultRepository;
    private FokontanyLocalElectionResultRepository fokontanyLocalElectionResultRepository;
    private MunicipalResultRepository municipalResultRepository;

    private ElectionService electionService;

    public LocalResultService(PollingStationLocalElectionResultRepository pollingStationLocalElectionResultRepository,
            FokontanyLocalElectionResultRepository fokontanyLocalElectionResultRepository,
            MunicipalResultRepository municipalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ElectionService electionService) {
        this.pollingStationLocalElectionResultRepository = pollingStationLocalElectionResultRepository;
        this.fokontanyLocalElectionResultRepository = fokontanyLocalElectionResultRepository;
        this.municipalResultRepository = municipalResultRepository;
        this.electionService = electionService;
    }

    public PagedModel<PollingStationLocalElectionResult> getPollingStationLocalElectionResults(Integer electionId,
            Pageable page) {
        Specification<PollingStationLocalElectionResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return new PagedModel<>(this.pollingStationLocalElectionResultRepository.findAll(spec, page));
    }

    public PagedModel<FokontanyLocalElectionResult> getFokontanyLocalElectionResults(Integer electionId,
            Pageable page) {
        Specification<FokontanyLocalElectionResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return new PagedModel<>(this.fokontanyLocalElectionResultRepository.findAll(spec, page));
    }

    public PagedModel<MunicipalResult> getMunicipalResults(Integer electionId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isLocal()) {
            throw new IllegalArgumentException("The election type is not a local election");
        }
        Specification<MunicipalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return new PagedModel<>(this.municipalResultRepository.findAll(spec, page));
    }
}
