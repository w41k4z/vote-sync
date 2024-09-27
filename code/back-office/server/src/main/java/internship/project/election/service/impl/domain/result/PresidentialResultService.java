package internship.project.election.service.impl.domain.result;

import org.springframework.stereotype.Service;

import internship.project.election.repository.result.details.CommunalResultDetailsRepository;
import internship.project.election.repository.result.details.DistrictResultDetailsRepository;
import internship.project.election.repository.result.details.FokontanyResultDetailsRepository;
import internship.project.election.repository.result.details.GlobalResultDetailsRepository;
import internship.project.election.repository.result.details.PollingStationResultDetailsRepository;
import internship.project.election.repository.result.details.ProvincialResultDetailsRepository;
import internship.project.election.repository.result.details.RegionalResultDetailsRepository;
import internship.project.election.repository.result.stat.CommunalResultStatRepository;
import internship.project.election.repository.result.stat.DistrictResultStatRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.GlobalResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;
import internship.project.election.repository.result.stat.ProvincialResultStatRepository;
import internship.project.election.repository.result.stat.RegionalResultStatRepository;

@Service
public class PresidentialResultService extends LegislativeResultService {

    private GlobalResultDetailsRepository globalResultDetailsRepository;
    private ProvincialResultDetailsRepository provincialResultDetailsRepository;
    private RegionalResultDetailsRepository regionalResultDetailsRepository;

    private GlobalResultStatRepository globalResultStatRepository;
    private ProvincialResultStatRepository provincialResultStatRepository;
    private RegionalResultStatRepository regionalResultStatRepository;

    public PresidentialResultService(GlobalResultDetailsRepository globalResultDetailsRepository,
            ProvincialResultDetailsRepository provincialResultDetailsRepository,
            RegionalResultDetailsRepository regionalResultDetailsRepository,
            DistrictResultDetailsRepository districtResultDetailsRepository,
            CommunalResultDetailsRepository communalResultDetailsRepository,
            FokontanyResultDetailsRepository fokontanyResultDetailsRepository,
            PollingStationResultDetailsRepository pollingStationResultDetailsRepository,
            GlobalResultStatRepository globalResultStatRepository,
            ProvincialResultStatRepository provincialResultStatRepository,
            RegionalResultStatRepository regionalResultStatRepository,
            DistrictResultStatRepository districtResultStatRepository,
            CommunalResultStatRepository communalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        super(districtResultDetailsRepository, communalResultDetailsRepository, fokontanyResultDetailsRepository,
                pollingStationResultDetailsRepository,
                districtResultStatRepository, communalResultStatRepository, fokontanyResultStatRepository,
                pollingStationResultStatRepository);
        this.globalResultDetailsRepository = globalResultDetailsRepository;
        this.provincialResultDetailsRepository = provincialResultDetailsRepository;
        this.regionalResultDetailsRepository = regionalResultDetailsRepository;

        this.globalResultStatRepository = globalResultStatRepository;
        this.provincialResultStatRepository = provincialResultStatRepository;
        this.regionalResultStatRepository = regionalResultStatRepository;
    }
}
