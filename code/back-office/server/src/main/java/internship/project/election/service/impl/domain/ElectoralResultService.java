package internship.project.election.service.impl.domain;

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
import internship.project.election.repository.result.stat.MunicipalResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;
import internship.project.election.repository.result.stat.ProvincialResultStatRepository;
import internship.project.election.repository.result.stat.RegionalResultStatRepository;

@Service
public class ElectoralResultService {

    private GlobalResultRepository globalResultRepository;
    private ProvincialResultRepository provincialResultRepository;
    private RegionalResultRepository regionalResultRepository;
    private DistrictResultRepository districtResultRepository;
    private MunicipalResultRepository municipalResultRepository;
    private CommunalResultRepository communalResultRepository;
    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    private GlobalResultStatRepository globalResultStatRepository;
    private ProvincialResultStatRepository provincialResultStatRepository;
    private RegionalResultStatRepository regionalResultStatRepository;
    private DistrictResultStatRepository districtResultStatRepository;
    private MunicipalResultStatRepository municipalResultStatRepository;
    private CommunalResultStatRepository communalResultStatRepository;
    private FokontanyResultStatRepository fokontanyResultStatRepository;
    private PollingStationResultStatRepository pollingStationResultStatRepository;

    public ElectoralResultService(GlobalResultRepository globalResultRepository,
            ProvincialResultRepository provincialResultRepository, RegionalResultRepository regionalResultRepository,
            DistrictResultRepository districtResultRepository, MunicipalResultRepository municipalResultRepository,
            CommunalResultRepository communalResultRepository, FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            GlobalResultStatRepository globalResultStatRepository,
            ProvincialResultStatRepository provincialResultStatRepository,
            RegionalResultStatRepository regionalResultStatRepository,
            DistrictResultStatRepository districtResultStatRepository,
            MunicipalResultStatRepository municipalResultStatRepository,
            CommunalResultStatRepository communalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        this.globalResultRepository = globalResultRepository;
        this.provincialResultRepository = provincialResultRepository;
        this.regionalResultRepository = regionalResultRepository;
        this.districtResultRepository = districtResultRepository;
        this.municipalResultRepository = municipalResultRepository;
        this.communalResultRepository = communalResultRepository;
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;

        this.globalResultStatRepository = globalResultStatRepository;
        this.provincialResultStatRepository = provincialResultStatRepository;
        this.regionalResultStatRepository = regionalResultStatRepository;
        this.districtResultStatRepository = districtResultStatRepository;
        this.municipalResultStatRepository = municipalResultStatRepository;
        this.communalResultStatRepository = communalResultStatRepository;
        this.fokontanyResultStatRepository = fokontanyResultStatRepository;
        this.pollingStationResultStatRepository = pollingStationResultStatRepository;
    }
}
