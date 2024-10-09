package internship.project.election.service.domain.result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import internship.project.election.model.Election;
import internship.project.election.model.result.CommunalResult;
import internship.project.election.model.result.DistrictResult;
import internship.project.election.repository.result.CommunalResultRepository;
import internship.project.election.repository.result.DistrictResultRepository;
import internship.project.election.repository.result.FokontanyResultRepository;
import internship.project.election.repository.result.PollingStationResultRepository;
import internship.project.election.service.domain.ElectionService;
import internship.project.election.service.domain.specification.ElectoralResultSpecification;

@Service
public class LegislativeResultService extends ElectoralResultService {

    private DistrictResultRepository districtResultRepository;
    private CommunalResultRepository communalResultRepository;

    public LegislativeResultService(DistrictResultRepository districtResultRepository,
            CommunalResultRepository communalResultRepository,
            FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ElectionService electionService) {
        super(fokontanyResultRepository, pollingStationResultRepository, electionService);
        this.districtResultRepository = districtResultRepository;
        this.communalResultRepository = communalResultRepository;
    }

    public List<CommunalResult> getCommunalResults(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !(election.get().getType().isLegislative()
                || election.get().getType().isPresidential())) {
            throw new IllegalArgumentException("The election type is not a legislative or presidential election");
        }
        Specification<CommunalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.communalResultRepository.findAll(spec);
    }

    public List<DistrictResult> getDistrictResults(Integer electionId) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty()
                && !(election.get().getType().isLegislative() || election.get().getType().isPresidential())) {
            throw new IllegalArgumentException("The election type is not a legislative or presidential election");
        }
        Specification<DistrictResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.districtResultRepository.findAll(spec);
    }
}
