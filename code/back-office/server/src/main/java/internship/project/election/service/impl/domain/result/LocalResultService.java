package internship.project.election.service.impl.domain.result;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.result.MunicipalResult;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.MunicipalResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.MunicipalResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

@Service
public class LocalResultService extends ElectionResultService {

    private MunicipalResultRepository municipalResultRepository;

    private MunicipalResultStatRepository municipalResultStatRepository;

    public LocalResultService(MunicipalResultRepository municipalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            MunicipalResultStatRepository municipalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        super(fokontanyResultRepository, pollingStationResultRepository, fokontanyResultStatRepository,
                pollingStationResultStatRepository);
        this.municipalResultRepository = municipalResultRepository;

        this.municipalResultStatRepository = municipalResultStatRepository;
    }

    public List<MunicipalResult> getMunicipalResults() {
        return this.municipalResultRepository.findAll();
    }
}
