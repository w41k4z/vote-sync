package ceni.system.votesync.service.domain.result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.Election;
import ceni.system.votesync.model.result.GlobalResult;
import ceni.system.votesync.model.result.ProvincialResult;
import ceni.system.votesync.model.result.RegionalResult;
import ceni.system.votesync.repository.result.CommunalResultRepository;
import ceni.system.votesync.repository.result.DistrictResultRepository;
import ceni.system.votesync.repository.result.FokontanyResultRepository;
import ceni.system.votesync.repository.result.GlobalResultRepository;
import ceni.system.votesync.repository.result.PollingStationResultRepository;
import ceni.system.votesync.repository.result.ProvincialResultRepository;
import ceni.system.votesync.repository.result.RegionalResultRepository;
import ceni.system.votesync.service.domain.ElectionService;
import ceni.system.votesync.service.domain.specification.ElectoralResultSpecification;

@Service
public class PresidentialResultService extends LegislativeResultService {

    private GlobalResultRepository globalResultRepository;
    private ProvincialResultRepository provincialResultRepository;
    private RegionalResultRepository regionalResultRepository;

    public PresidentialResultService(GlobalResultRepository globalResultRepository,
            ProvincialResultRepository provincialResultRepository,
            RegionalResultRepository regionalResultRepository,
            DistrictResultRepository districtResultRepository,
            CommunalResultRepository communalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ElectionService electionService) {
        super(districtResultRepository, communalResultRepository, fokontanyResultRepository,
                pollingStationResultRepository, electionService);
        this.globalResultRepository = globalResultRepository;
        this.provincialResultRepository = provincialResultRepository;
        this.regionalResultRepository = regionalResultRepository;
    }

    public GlobalResult getGlobalResult(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new IllegalArgumentException("The election type is not a presidential election");
        }
        Specification<GlobalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.globalResultRepository.findAll(spec).get(0);
    }

    public List<ProvincialResult> getProvincialResults(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new IllegalArgumentException("The election type is not a presidential election");
        }
        Specification<ProvincialResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.provincialResultRepository.findAll(spec);
    }

    public List<RegionalResult> getRegionalResults(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new IllegalArgumentException("The election type is not a presidential election");
        }
        Specification<RegionalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.regionalResultRepository.findAll(spec);
    }
}