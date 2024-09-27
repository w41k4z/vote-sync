package internship.project.election.service.impl.domain.result;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.result.details.MunicipalResultDetails;
import internship.project.election.repository.result.details.FokontanyResultDetailsRepository;
import internship.project.election.repository.result.details.MunicipalResultDetailsRepository;
import internship.project.election.repository.result.details.PollingStationResultDetailsRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.MunicipalResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;

@Service
public class LocalResultService extends ElectionResultService {

    private MunicipalResultDetailsRepository municipalResultDetailsRepository;

    private MunicipalResultStatRepository municipalResultStatRepository;

    public LocalResultService(MunicipalResultDetailsRepository municipalResultDetailsRepository,
            FokontanyResultDetailsRepository fokontanyResultDetailsRepository,
            PollingStationResultDetailsRepository pollingStationResultDetailsRepository,
            MunicipalResultStatRepository municipalResultStatRepository,
            FokontanyResultStatRepository fokontanyResultStatRepository,
            PollingStationResultStatRepository pollingStationResultStatRepository) {
        super(fokontanyResultDetailsRepository, pollingStationResultDetailsRepository, fokontanyResultStatRepository,
                pollingStationResultStatRepository);
        this.municipalResultDetailsRepository = municipalResultDetailsRepository;

        this.municipalResultStatRepository = municipalResultStatRepository;
    }

    public List<MunicipalResultDetails> getMunicipalResultDetails() {
        return this.municipalResultDetailsRepository.findAll();
    }
}
