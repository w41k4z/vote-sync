package ceni.system.votesync.service.entity.result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.election.Election;
import ceni.system.votesync.model.view.election.result.GlobalResult;
import ceni.system.votesync.model.view.election.result.ProvincialResult;
import ceni.system.votesync.model.view.election.result.RegionalResult;
import ceni.system.votesync.repository.view.election.result.CommunalResultRepository;
import ceni.system.votesync.repository.view.election.result.DistrictResultRepository;
import ceni.system.votesync.repository.view.election.result.FokontanyResultRepository;
import ceni.system.votesync.repository.view.election.result.GlobalResultRepository;
import ceni.system.votesync.repository.view.election.result.PollingStationResultRepository;
import ceni.system.votesync.repository.view.election.result.ProvincialResultRepository;
import ceni.system.votesync.repository.view.election.result.RegionalResultRepository;
import ceni.system.votesync.service.entity.election.ElectionService;
import ceni.system.votesync.exception.WrongElectionTypeException;

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
            throw new WrongElectionTypeException("The election type is not a presidential election");
        }
        Specification<GlobalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.globalResultRepository.findAll(spec).get(0);
    }

    public List<ProvincialResult> getProvincialResults(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new WrongElectionTypeException("The election type is not a presidential election");
        }
        Specification<ProvincialResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.provincialResultRepository.findAll(spec);
    }

    public List<RegionalResult> getRegionalResults(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isPresidential()) {
            throw new WrongElectionTypeException("The election type is not a presidential election");
        }
        Specification<RegionalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.regionalResultRepository.findAll(spec);
    }
}