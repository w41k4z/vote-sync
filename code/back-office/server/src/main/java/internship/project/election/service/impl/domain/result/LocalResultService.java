package internship.project.election.service.impl.domain.result;

import org.springframework.stereotype.Service;

import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.MunicipalResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.MunicipalResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

@Service
public class LocalResultService {

    private MunicipalResultRepository municipalResultRepository;
    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    private MunicipalResultStatRepository municipalResultStatRepository;
    private FokontanyResultStatRepository fokontanyResultStatRepository;
    private PollingStationResultStatRepository pollingStationResultStatRepository;

    public LocalResultService(MunicipalResultRepository municipalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            MunicipalResultStatRepository municipalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        this.municipalResultRepository = municipalResultRepository;
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;

        this.municipalResultStatRepository = municipalResultStatRepository;
        this.fokontanyResultStatRepository = fokontanyResultStatRepository;
        this.pollingStationResultStatRepository = pollingStationResultStatRepository;
    }

}
