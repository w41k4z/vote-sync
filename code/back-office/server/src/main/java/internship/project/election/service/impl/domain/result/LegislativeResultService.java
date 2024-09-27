package internship.project.election.service.impl.domain.result;

import org.springframework.stereotype.Service;

import internship.project.election.repository.result.CommunalResultRepository;
import internship.project.election.repository.result.DistrictResultRepository;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.stat.CommunalResultStatRepository;
import internship.project.election.repository.result.stat.DistrictResultStatRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

@Service
public class LegislativeResultService {

    private DistrictResultRepository districtResultRepository;
    private CommunalResultRepository communalResultRepository;
    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    private DistrictResultStatRepository districtResultStatRepository;
    private CommunalResultStatRepository communalResultStatRepository;
    private FokontanyResultStatRepository fokontanyResultStatRepository;
    private PollingStationResultStatRepository pollingStationResultStatRepository;

    public LegislativeResultService(DistrictResultRepository districtResultRepository,
            CommunalResultRepository communalResultRepository, FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            DistrictResultStatRepository districtResultStatRepository,
            CommunalResultStatRepository communalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        this.districtResultRepository = districtResultRepository;
        this.communalResultRepository = communalResultRepository;
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;

        this.districtResultStatRepository = districtResultStatRepository;
        this.communalResultStatRepository = communalResultStatRepository;
        this.fokontanyResultStatRepository = fokontanyResultStatRepository;
        this.pollingStationResultStatRepository = pollingStationResultStatRepository;
    }
}
