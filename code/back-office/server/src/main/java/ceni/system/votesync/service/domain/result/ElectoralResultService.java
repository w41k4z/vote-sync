package ceni.system.votesync.service.domain.result;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.result.FokontanyResult;
import ceni.system.votesync.model.result.PollingStationResult;
import ceni.system.votesync.repository.result.FokontanyResultRepository;
import ceni.system.votesync.repository.result.PollingStationResultRepository;
import ceni.system.votesync.service.domain.ElectionService;
import ceni.system.votesync.service.domain.specification.ElectoralResultSpecification;

class ElectoralResultService {

    private FokontanyResultRepository fokontanyResultRepository;
    private PollingStationResultRepository pollingStationResultRepository;

    protected ElectionService electionService;

    public ElectoralResultService(FokontanyResultRepository fokontanyResultRepository,
            PollingStationResultRepository pollingStationResultRepository,
            ElectionService electionService) {
        this.fokontanyResultRepository = fokontanyResultRepository;
        this.pollingStationResultRepository = pollingStationResultRepository;

        this.electionService = electionService;
    }

    public List<PollingStationResult> getPollingStationResults(Integer electionId) {
        Specification<PollingStationResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.pollingStationResultRepository.findAll(spec);
    }

    public List<FokontanyResult> getFokontanyResults(Integer electionId) {
        Specification<FokontanyResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return this.fokontanyResultRepository.findAll(spec);
    }
}
