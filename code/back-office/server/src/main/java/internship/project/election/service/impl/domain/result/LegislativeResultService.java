package internship.project.election.service.impl.domain.result;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.result.CommunalResult;
import internship.project.election.model.result.DistrictResult;
import internship.project.election.repository.result.CommunalResultRepository;
import internship.project.election.repository.result.DistrictResultRepository;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.repository.result.stat.CommunalResultStatRepository;
import internship.project.election.repository.result.stat.DistrictResultStatRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

@Service
public class LegislativeResultService extends ElectionResultService {

    private DistrictResultRepository districtResultRepository;
    private CommunalResultRepository communalResultRepository;

    private DistrictResultStatRepository districtResultStatRepository;
    private CommunalResultStatRepository communalResultStatRepository;

    public LegislativeResultService(DistrictResultRepository districtResultRepository,
            CommunalResultRepository communalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            DistrictResultStatRepository districtResultStatRepository,
            CommunalResultStatRepository communalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        super(fokontanyResultRepository, pollingStationResultRepository, fokontanyResultStatRepository,
                pollingStationResultStatRepository);
        this.districtResultRepository = districtResultRepository;
        this.communalResultRepository = communalResultRepository;

        this.districtResultStatRepository = districtResultStatRepository;
        this.communalResultStatRepository = communalResultStatRepository;
    }

    public List<CommunalResult> getCommunalResults() {
        return this.communalResultRepository.findAll();
    }

    public List<DistrictResult> getDistrictResults() {
        return this.districtResultRepository.findAll();
    }
}
