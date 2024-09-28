package internship.project.election.service.impl.domain.result;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.model.result.FokontanyResult;
import internship.project.election.model.result.PollingStationResult;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;
import internship.project.election.service.impl.domain.ElectionService;
import internship.project.election.service.impl.domain.specification.ElectoralResultSpecification;

class ElectoralResultService {

    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    private FokontanyResultStatRepository fokontanyResultStatRepository;
    private PollingStationResultStatRepository pollingStationResultStatRepository;

    protected ElectionService electionService;

    public ElectoralResultService(FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository,
            ElectionService electionService) {
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;

        this.fokontanyResultStatRepository = fokontanyResultStatRepository;
        this.pollingStationResultStatRepository = pollingStationResultStatRepository;

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
