package ceni.system.votesync.service.domain.result;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.Election;
import ceni.system.votesync.model.result.CommunalResult;
import ceni.system.votesync.model.result.DistrictResult;
import ceni.system.votesync.repository.result.CommunalResultRepository;
import ceni.system.votesync.repository.result.DistrictResultRepository;
import ceni.system.votesync.repository.result.FokontanyResultRepository;
import ceni.system.votesync.repository.result.PollingStationResultRepository;
import ceni.system.votesync.service.domain.ElectionService;
import ceni.system.votesync.service.domain.specification.ElectoralResultSpecification;

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
