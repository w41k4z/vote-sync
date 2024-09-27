package internship.project.election.service.impl.domain.result;

import org.springframework.stereotype.Service;

import internship.project.election.repository.result.details.CommunalResultDetailsRepository;
import internship.project.election.repository.result.details.DistrictResultDetailsRepository;
import internship.project.election.repository.result.details.FokontanyResultDetailsRepository;
import internship.project.election.repository.result.details.PollingStationResultDetailsRepository;
import internship.project.election.repository.result.stat.CommunalResultStatRepository;
import internship.project.election.repository.result.stat.DistrictResultStatRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

@Service
public class LegislativeResultService extends ElectionResultService {

    private DistrictResultDetailsRepository districtResultDetailsRepository;
    private CommunalResultDetailsRepository communalResultDetailsRepository;

    private DistrictResultStatRepository districtResultStatRepository;
    private CommunalResultStatRepository communalResultStatRepository;

    public LegislativeResultService(DistrictResultDetailsRepository districtResultDetailsRepository,
            CommunalResultDetailsRepository communalResultDetailsRepository,
            FokontanyResultDetailsRepository fokontanyResultDetailsRepository,
            PollingStationResultDetailsRepository pollingStationResultDetailsRepository,
            DistrictResultStatRepository districtResultStatRepository,
            CommunalResultStatRepository communalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        super(fokontanyResultDetailsRepository, pollingStationResultDetailsRepository, fokontanyResultStatRepository,
                pollingStationResultStatRepository);
        this.districtResultDetailsRepository = districtResultDetailsRepository;
        this.communalResultDetailsRepository = communalResultDetailsRepository;

        this.districtResultStatRepository = districtResultStatRepository;
        this.communalResultStatRepository = communalResultStatRepository;
    }
}
