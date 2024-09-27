package internship.project.election.service.impl.domain.result;

import java.util.List;

import internship.project.election.model.result.FokontanyResult;
import internship.project.election.model.result.details.FokontanyResultDetails;
import internship.project.election.model.result.details.PollingStationResultDetails;
import internship.project.election.repository.result.details.FokontanyResultDetailsRepository;
import internship.project.election.repository.result.details.PollingStationResultDetailsRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

class ElectionResultService {

    private FokontanyResultDetailsRepository fokontanyResultDetailsRepository;
    private PollingStationResultDetailsRepository pollingStationResultDetailsRepository;

    private FokontanyResultStatRepository fokontanyResultStatRepository;
    private PollingStationResultStatRepository pollingStationResultStatRepository;

    public ElectionResultService(FokontanyResultDetailsRepository fokontanyResultDetailsRepository,
            PollingStationResultDetailsRepository pollingStationResultDetailsRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        this.fokontanyResultDetailsRepository = fokontanyResultDetailsRepository;
        this.pollingStationResultDetailsRepository = pollingStationResultDetailsRepository;

        this.fokontanyResultStatRepository = fokontanyResultStatRepository;
        this.pollingStationResultStatRepository = pollingStationResultStatRepository;
    }

    public List<PollingStationResultDetails> getPollingStationResultDetails() {
        return this.pollingStationResultDetailsRepository.findAll();
    }

    public List<FokontanyResultDetails> getFokontanyResultDetails() {
        return this.fokontanyResultDetailsRepository.findAll();
    }
}
