package internship.project.election.service.domain.result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import internship.project.election.model.Election;
import internship.project.election.model.result.GlobalResult;
import internship.project.election.model.result.ProvincialResult;
import internship.project.election.model.result.RegionalResult;
import internship.project.election.repository.result.CommunalResultRepository;
import internship.project.election.repository.result.DistrictResultRepository;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.GlobalResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.ProvincialResultRepository;
import internship.project.election.repository.result.RegionalResultRepository;
import internship.project.election.service.domain.ElectionService;
import internship.project.election.service.domain.specification.ElectoralResultSpecification;

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