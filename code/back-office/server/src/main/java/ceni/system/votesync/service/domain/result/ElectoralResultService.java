package ceni.system.votesync.service.domain.result;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;

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

    public PagedModel<PollingStationResult> getPollingStationResults(Integer electionId, Pageable page) {
        Specification<PollingStationResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return new PagedModel<>(this.pollingStationResultRepository.findAll(spec, page));
    }

    public PagedModel<FokontanyResult> getFokontanyResults(Integer electionId, Pageable page) {
        Specification<FokontanyResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return new PagedModel<>(this.fokontanyResultRepository.findAll(spec, page));
    }
}
