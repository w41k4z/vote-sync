package internship.project.election.service.domain.result;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.model.result.FokontanyResult;
import internship.project.election.model.result.PollingStationResult;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.service.domain.ElectionService;
import internship.project.election.service.domain.specification.ElectoralResultSpecification;

class ElectoralResultService {

    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    protected ElectionService electionService;

    public ElectoralResultService(FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ElectionService electionService) {
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;

        this.electionService = electionService;
    }

    public List<PollingStationResult> getPollingStationResults(Integer electionId) {
        Specification<PollingStationResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.pollingStationResultRepository.findAll(spec);
    }

    public List<FokontanyResult> getFokontanyResults(Integer electionId) {
        Specification<FokontanyResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.fokontanyResultRepository.findAll(spec);
    }
}
