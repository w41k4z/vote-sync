package ceni.system.votesync.service.entity.result;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalFokontanyResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalMunicipalResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalPollingStationResult;
import ceni.system.votesync.model.view.election.result.FokontanyLocalElectionResult;
import ceni.system.votesync.model.view.election.result.MunicipalResult;
import ceni.system.votesync.model.view.election.result.PollingStationLocalElectionResult;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalFokontanyResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalMunicipalResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalPollingStationResultRepository;
import ceni.system.votesync.repository.view.election.result.FokontanyLocalElectionResultRepository;
import ceni.system.votesync.repository.view.election.result.FokontanyResultRepository;
import ceni.system.votesync.repository.view.election.result.MunicipalResultRepository;
import ceni.system.votesync.repository.view.election.result.PollingStationLocalElectionResultRepository;
import ceni.system.votesync.repository.view.election.result.PollingStationResultRepository;
import ceni.system.votesync.service.entity.election.ElectionService;
import ceni.system.votesync.config.Status;
import ceni.system.votesync.exception.WrongElectionTypeException;

@Service
public class LocalResultService {

    private PollingStationLocalElectionResultRepository pollingStationLocalElectionResultRepository;
    private FokontanyLocalElectionResultRepository fokontanyLocalElectionResultRepository;
    private MunicipalResultRepository municipalResultRepository;

    private ProvisionalPollingStationResultRepository provisionalPollingStationResultRepository;
    private ProvisionalFokontanyResultRepository provisionalFokontanyResultRepository;
    private ProvisionalMunicipalResultRepository provisionalMunicipalResultRepository;

    private ElectionService electionService;

    public LocalResultService(PollingStationLocalElectionResultRepository pollingStationLocalElectionResultRepository,
            FokontanyLocalElectionResultRepository fokontanyLocalElectionResultRepository,
            MunicipalResultRepository municipalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ProvisionalPollingStationResultRepository provisionalPollingStationResultRepository,
            ProvisionalFokontanyResultRepository provisionalFokontanyResultRepository,
            ProvisionalMunicipalResultRepository provisionalMunicipalResultRepository,
            ElectionService electionService) {
        this.pollingStationLocalElectionResultRepository = pollingStationLocalElectionResultRepository;
        this.fokontanyLocalElectionResultRepository = fokontanyLocalElectionResultRepository;
        this.municipalResultRepository = municipalResultRepository;
        this.provisionalPollingStationResultRepository = provisionalPollingStationResultRepository;
        this.provisionalFokontanyResultRepository = provisionalFokontanyResultRepository;
        this.provisionalMunicipalResultRepository = provisionalMunicipalResultRepository;
        this.electionService = electionService;
    }

    public Object getPollingStationLocalElectionResults(Integer electionId,
            Integer regionId, Integer districtId, Integer municipalityId,
            Integer fokontanyId,
            Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        Specification<PollingStationLocalElectionResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        spec = spec.and(ElectoralResultSpecification.filterPollingStationLocalResult(regionId, districtId,
                municipalityId, fokontanyId));
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalPollingStationResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            provisionalSpec = provisionalSpec.and(ProvisionalElectoralResultSpecification
                    .filterProvisionalPollingStationLocalResult(regionId, districtId, municipalityId, fokontanyId));
            return new PagedModel<>(this.provisionalPollingStationResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.pollingStationLocalElectionResultRepository.findAll(spec, page));
        }
    }

    public Object getFokontanyLocalElectionResults(Integer electionId, Integer regionId, Integer districtId,
            Integer municipalityId,
            Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        Specification<FokontanyLocalElectionResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        spec = spec.and(ElectoralResultSpecification.filterFokontanyLocalResult(regionId, districtId, municipalityId));
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalFokontanyResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            provisionalSpec = provisionalSpec.and(ProvisionalElectoralResultSpecification
                    .filterProvisionalFokontanyLocalResult(regionId, districtId, municipalityId));
            return new PagedModel<>(this.provisionalFokontanyResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.fokontanyLocalElectionResultRepository.findAll(spec, page));
        }
    }

    public Object getMunicipalResults(Integer electionId, Integer regionId, Integer districtId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isLocal()) {
            throw new WrongElectionTypeException("The election type is not a local election");
        }
        Specification<MunicipalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        spec = spec.and(ElectoralResultSpecification.filterMunicipalResult(regionId, districtId));
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalMunicipalResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            provisionalSpec = provisionalSpec.and(ProvisionalElectoralResultSpecification
                    .filterProvisionalMunicipalResult(regionId, districtId));
            return new PagedModel<>(this.provisionalMunicipalResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.municipalResultRepository.findAll(spec, page));
        }
    }
}
