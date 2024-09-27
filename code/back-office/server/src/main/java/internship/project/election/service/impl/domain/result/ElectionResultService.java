package internship.project.election.service.impl.domain.result;

import java.util.List;

import internship.project.election.model.result.FokontanyResult;
import internship.project.election.model.result.PollingStationResult;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

class ElectionResultService {

    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    private FokontanyResultStatRepository fokontanyResultStatRepository;
    private PollingStationResultStatRepository pollingStationResultStatRepository;

    public ElectionResultService(FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;

        this.fokontanyResultStatRepository = fokontanyResultStatRepository;
        this.pollingStationResultStatRepository = pollingStationResultStatRepository;
    }

    public List<PollingStationResult> getPollingStationResults() {
        return this.pollingStationResultRepository.findAll();
    }

    public List<FokontanyResult> getFokontanyResults() {
        return this.fokontanyResultRepository.findAll();
    }
}
