package ceni.system.votesync.service.entity.result;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalCommunalResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalDistrictResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalFokontanyResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalPollingStationResult;
import ceni.system.votesync.model.view.election.result.CommunalResult;
import ceni.system.votesync.model.view.election.result.DistrictResult;
import ceni.system.votesync.model.view.election.result.FokontanyResult;
import ceni.system.votesync.model.view.election.result.PollingStationResult;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalCommunalResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalDistrictResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalFokontanyResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalPollingStationResultRepository;
import ceni.system.votesync.repository.view.election.result.CommunalResultRepository;
import ceni.system.votesync.repository.view.election.result.DistrictResultRepository;
import ceni.system.votesync.repository.view.election.result.FokontanyResultRepository;
import ceni.system.votesync.repository.view.election.result.PollingStationResultRepository;
import ceni.system.votesync.service.entity.election.ElectionService;
import ceni.system.votesync.config.Status;
import ceni.system.votesync.exception.WrongElectionTypeException;

@Service
public class LegislativeResultService {

    private DistrictResultRepository districtResultRepository;
    private CommunalResultRepository communalResultRepository;
    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    private ProvisionalPollingStationResultRepository provisionalPollingStationResultRepository;
    private ProvisionalFokontanyResultRepository provisionalFokontanyResultRepository;
    private ProvisionalCommunalResultRepository provisionalCommunalResultRepository;
    private ProvisionalDistrictResultRepository provisionalDistrictResultRepository;

    protected ElectionService electionService;

    public LegislativeResultService(DistrictResultRepository districtResultRepository,
            CommunalResultRepository communalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ProvisionalPollingStationResultRepository provisionalPollingStationResultRepository,
            ProvisionalFokontanyResultRepository provisionalFokontanyResultRepository,
            ProvisionalCommunalResultRepository provisionalCommunalResultRepository,
            ProvisionalDistrictResultRepository provisionalDistrictResultRepository,
            ElectionService electionService) {
        this.districtResultRepository = districtResultRepository;
        this.communalResultRepository = communalResultRepository;
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;
        this.provisionalPollingStationResultRepository = provisionalPollingStationResultRepository;
        this.provisionalFokontanyResultRepository = provisionalFokontanyResultRepository;
        this.provisionalCommunalResultRepository = provisionalCommunalResultRepository;
        this.provisionalDistrictResultRepository = provisionalDistrictResultRepository;
        this.electionService = electionService;
    }

    public Object getPollingStationResults(Integer electionId, Integer regionId,
            Integer districtId, Integer communeId, Integer fokontanyId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        Specification<PollingStationResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        spec = spec.and(ElectoralResultSpecification.filterPollingStationResult(regionId, districtId,
                communeId, fokontanyId));
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalPollingStationResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            provisionalSpec = provisionalSpec.and(ProvisionalElectoralResultSpecification
                    .filterProvisionalPollingStationResult(regionId, districtId, communeId, fokontanyId));
            return new PagedModel<>(this.provisionalPollingStationResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.pollingStationResultRepository.findAll(spec, page));
        }
    }

    public Object getFokontanyResults(Integer electionId, Integer regionId,
            Integer districtId, Integer communeId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        Specification<FokontanyResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        spec = spec.and(ElectoralResultSpecification.filterFokontanyResult(regionId, districtId, communeId));
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalFokontanyResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            provisionalSpec = provisionalSpec.and(ProvisionalElectoralResultSpecification
                    .filterProvisionalFokontanyResult(regionId, districtId, communeId));
            return new PagedModel<>(this.provisionalFokontanyResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.fokontanyResultRepository.findAll(spec, page));
        }
    }

    public Object getCommunalResults(Integer electionId, Integer regionId, Integer districtId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !(election.get().getType().isLegislative()
                || election.get().getType().isPresidential())) {
            throw new WrongElectionTypeException("The election type is not a legislative or presidential election");
        }
        Specification<CommunalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        spec = spec.and(ElectoralResultSpecification.filterCommunalResult(regionId, districtId));
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalCommunalResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            provisionalSpec = provisionalSpec.and(ProvisionalElectoralResultSpecification
                    .filterProvisionalCommunalResult(regionId, districtId));
            return new PagedModel<>(this.provisionalCommunalResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.communalResultRepository.findAll(spec, page));
        }
    }

    public Object getDistrictResults(Integer electionId, Integer regionId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty()
                && !(election.get().getType().isLegislative() || election.get().getType().isPresidential())) {
            throw new WrongElectionTypeException("The election type is not a legislative or presidential election");
        }
        Specification<DistrictResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        spec = spec.and(ElectoralResultSpecification.filterDistrictResult(regionId));
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalDistrictResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            provisionalSpec = provisionalSpec.and(ProvisionalElectoralResultSpecification
                    .filterProvisionalDistrictResult(regionId));
            return new PagedModel<>(this.provisionalDistrictResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.districtResultRepository.findAll(spec, page));
        }
    }
}
