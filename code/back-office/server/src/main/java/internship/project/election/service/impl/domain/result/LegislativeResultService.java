package internship.project.election.service.impl.domain.result;

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
import internship.project.election.repository.result.stat.CommunalResultStatRepository;
import internship.project.election.repository.result.stat.DistrictResultStatRepository;
import internship.project.election.repository.result.stat.FokontanyResultStatRepository;
import internship.project.election.repository.result.stat.PollingStationResultStatRepository;
import internship.project.election.service.impl.domain.ElectionService;
import internship.project.election.service.impl.domain.specification.ElectoralResultSpecification;

@Service
public class LegislativeResultService extends ElectoralResultService {

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
            PollingStationResultStatRepository pollingStationResultStatRepository,
            ElectionService electionService) {
        super(fokontanyResultRepository, pollingStationResultRepository, fokontanyResultStatRepository,
                pollingStationResultStatRepository, electionService);
        this.districtResultRepository = districtResultRepository;
        this.communalResultRepository = communalResultRepository;

        this.districtResultStatRepository = districtResultStatRepository;
        this.communalResultStatRepository = communalResultStatRepository;
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
