package ceni.system.votesync.service.entity.result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalGlobalResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalProvincialResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalRegionalResult;
import ceni.system.votesync.model.view.election.result.GlobalResult;
import ceni.system.votesync.model.view.election.result.ProvincialResult;
import ceni.system.votesync.model.view.election.result.RegionalResult;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalCommunalResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalDistrictResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalFokontanyResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalGlobalResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalPollingStationResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalProvincialResultRepository;
import ceni.system.votesync.repository.entity.election.result.provisional.ProvisionalRegionalResultRepository;
import ceni.system.votesync.repository.view.election.result.CommunalResultRepository;
import ceni.system.votesync.repository.view.election.result.DistrictResultRepository;
import ceni.system.votesync.repository.view.election.result.FokontanyResultRepository;
import ceni.system.votesync.repository.view.election.result.GlobalResultRepository;
import ceni.system.votesync.repository.view.election.result.PollingStationResultRepository;
import ceni.system.votesync.repository.view.election.result.ProvincialResultRepository;
import ceni.system.votesync.repository.view.election.result.RegionalResultRepository;
import ceni.system.votesync.service.entity.election.ElectionService;
import ceni.system.votesync.config.Status;
import ceni.system.votesync.exception.WrongElectionTypeException;

@Service
public class PresidentialResultService extends LegislativeResultService {

    private GlobalResultRepository globalResultRepository;
    private ProvincialResultRepository provincialResultRepository;
    private RegionalResultRepository regionalResultRepository;

    private ProvisionalGlobalResultRepository provisionalGlobalResultRepository;
    private ProvisionalProvincialResultRepository provisionalProvincialResultRepository;
    private ProvisionalRegionalResultRepository provisionalRegionalResultRepository;

    public PresidentialResultService(GlobalResultRepository globalResultRepository,
            ProvincialResultRepository provincialResultRepository,
            RegionalResultRepository regionalResultRepository,
            DistrictResultRepository districtResultRepository,
            CommunalResultRepository communalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ProvisionalGlobalResultRepository provisionalGlobalResultRepository,
            ProvisionalProvincialResultRepository provisionalProvincialResultRepository,
            ProvisionalRegionalResultRepository provisionalRegionalResultRepository,
            ProvisionalPollingStationResultRepository provisionalPollingStationResultRepository,
            ProvisionalFokontanyResultRepository provisionalFokontanyResultRepository,
            ProvisionalCommunalResultRepository provisionalCommunalResultRepository,
            ProvisionalDistrictResultRepository provisionalDistrictResultRepository,
            ElectionService electionService) {
        super(districtResultRepository, communalResultRepository, fokontanyResultRepository,
                pollingStationResultRepository, provisionalPollingStationResultRepository,
                provisionalFokontanyResultRepository, provisionalCommunalResultRepository,
                provisionalDistrictResultRepository, electionService);
        this.globalResultRepository = globalResultRepository;
        this.provincialResultRepository = provincialResultRepository;
        this.regionalResultRepository = regionalResultRepository;
        this.provisionalGlobalResultRepository = provisionalGlobalResultRepository;
        this.provisionalProvincialResultRepository = provisionalProvincialResultRepository;
        this.provisionalRegionalResultRepository = provisionalRegionalResultRepository;
    }

    public Object getGlobalResult(Integer electionId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new WrongElectionTypeException("The election type is not a presidential election");
        }
        Specification<GlobalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalGlobalResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            return new PagedModel<>(this.provisionalGlobalResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.globalResultRepository.findAll(spec, page));
        }
    }

    public List<ProvisionalGlobalResult> getGlobalResult(Integer electionId) {
        Specification<ProvisionalGlobalResult> provisionalSpec = ProvisionalElectoralResultSpecification
                .withElectionId(electionId);
        return this.provisionalGlobalResultRepository.findAll(provisionalSpec);
    }

    public Object getProvincialResults(Integer electionId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new WrongElectionTypeException("The election type is not a presidential election");
        }
        Specification<ProvincialResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalProvincialResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            return new PagedModel<>(this.provisionalProvincialResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.provincialResultRepository.findAll(spec, page));
        }
    }

    public List<ProvisionalProvincialResult> getProvincialResults(Integer electionId) {
        Specification<ProvisionalProvincialResult> provisionalSpec = ProvisionalElectoralResultSpecification
                .withElectionId(electionId);
        return this.provisionalProvincialResultRepository.findAll(provisionalSpec);
    }

    public Object getRegionalResults(Integer electionId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new WrongElectionTypeException("The election type is not a presidential election");
        }
        Specification<RegionalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        if (election.get().getStatus() == Status.CLOSED) {
            Specification<ProvisionalRegionalResult> provisionalSpec = ProvisionalElectoralResultSpecification
                    .withElectionId(electionId);
            return new PagedModel<>(this.provisionalRegionalResultRepository.findAll(provisionalSpec, page));
        } else {
            return new PagedModel<>(this.regionalResultRepository.findAll(spec, page));
        }
    }

    public List<ProvisionalRegionalResult> getRegionalResults(Integer electionId) {
        Specification<ProvisionalRegionalResult> provisionalSpec = ProvisionalElectoralResultSpecification
                .withElectionId(electionId);
        return this.provisionalRegionalResultRepository.findAll(provisionalSpec);
    }
}