package ceni.system.votesync.service.domain.result;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.Election;
import ceni.system.votesync.model.result.MunicipalResult;
import ceni.system.votesync.repository.result.FokontanyResultRepository;
import ceni.system.votesync.repository.result.MunicipalResultRepository;
import ceni.system.votesync.repository.result.PollingStationResultRepository;
import ceni.system.votesync.service.domain.ElectionService;
import ceni.system.votesync.service.domain.specification.ElectoralResultSpecification;

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

    public PagedModel<MunicipalResult> getMunicipalResults(Integer electionId, Pageable page) {
        Optional<Election> election = this.electionService.getElection(electionId);
        if (!election.isEmpty() && !election.get().getType().isLocal()) {
            throw new IllegalArgumentException("The election type is not a local election");
        }
        Specification<MunicipalResult> spec = ElectoralResultSpecification.withElectionId(electionId);
        return new PagedModel<>(this.municipalResultRepository.findAll(spec, page));
    }
}
