package internship.project.election.service.domain.result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import internship.project.election.model.Election;
import internship.project.election.model.result.MunicipalResult;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.MunicipalResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.service.domain.ElectionService;
import internship.project.election.service.domain.specification.ElectoralResultSpecification;

@Service
public class LocalResultService extends ElectoralResultService {

    private MunicipalResultRepository municipalResultRepository;

    public LocalResultService(MunicipalResultRepository municipalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ElectionService electionService) {
        super(fokontanyResultRepository, pollingStationResultRepository, electionService);
        this.municipalResultRepository = municipalResultRepository;
    }

    public List<MunicipalResult> getMunicipalResults(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isLocal()) {
            throw new IllegalArgumentException("The election type is not a local election");
        }
        Specification<MunicipalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.municipalResultRepository.findAll(spec);
    }
}
