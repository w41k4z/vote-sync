package internship.project.election.service.impl.domain.result;

import org.springframework.stereotype.Service;

import internship.project.election.repository.result.CommunalResultRepository;
import internship.project.election.repository.result.DistrictResultRepository;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.GlobalResultRepository;
import internship.project.election.repository.result.MunicipalResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.ProvincialResultRepository;
import internship.project.election.repository.result.RegionalResultRepository;
import internship.project.election.repository.result.stat.CommunalResultStatRepository;
import internship.project.election.repository.result.stat.DistrictResultStatRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.GlobalResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;
import internship.project.election.repository.result.stat.ProvincialResultStatRepository;
import internship.project.election.repository.result.stat.RegionalResultStatRepository;

@Service
public class PresidentialResultService extends LegislativeResultService {

    private GlobalResultRepository globalResultRepository;
    private ProvincialResultRepository provincialResultRepository;
    private RegionalResultRepository regionalResultRepository;

    private GlobalResultStatRepository globalResultStatRepository;
    private ProvincialResultStatRepository provincialResultStatRepository;
    private RegionalResultStatRepository regionalResultStatRepository;

    public PresidentialResultService(GlobalResultRepository globalResultRepository,
            ProvincialResultRepository provincialResultRepository, RegionalResultRepository regionalResultRepository,
            DistrictResultRepository districtResultRepository,
            CommunalResultRepository communalResultRepository, FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            GlobalResultStatRepository globalResultStatRepository,
            ProvincialResultStatRepository provincialResultStatRepository,
            RegionalResultStatRepository regionalResultStatRepository,
            DistrictResultStatRepository districtResultStatRepository,
            CommunalResultStatRepository communalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        super(districtResultRepository, communalResultRepository, fokontanyResultRepository,
                pollingStationResultRepository,
                districtResultStatRepository, communalResultStatRepository, fokontanyResultStatRepository,
                pollingStationResultStatRepository);
        this.globalResultRepository = globalResultRepository;
        this.provincialResultRepository = provincialResultRepository;
        this.regionalResultRepository = regionalResultRepository;

        this.globalResultStatRepository = globalResultStatRepository;
        this.provincialResultStatRepository = provincialResultStatRepository;
        this.regionalResultStatRepository = regionalResultStatRepository;
    }
}
