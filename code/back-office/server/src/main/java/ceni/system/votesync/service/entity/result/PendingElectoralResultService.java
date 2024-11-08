package ceni.system.votesync.service.entity.result;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ceni.system.votesync.dto.request.result.ValidateElectoralResultRequest;
import ceni.system.votesync.model.view.election.result.PendingElectoralResult;
import ceni.system.votesync.repository.view.election.result.PendingElectoralResultRepository;
import ceni.system.votesync.service.spec.auth.AuthService;

@Service
public class PendingElectoralResultService {

    private PendingElectoralResultRepository pendingElectoralResultRepository;
    private ElectoralResultUploadService electoralResultUploadService;

    public PendingElectoralResultService(PendingElectoralResultRepository pendingElectoralResultRepository,
            ElectoralResultUploadService electoralResultUploadService) {
        this.pendingElectoralResultRepository = pendingElectoralResultRepository;
        this.electoralResultUploadService = electoralResultUploadService;
    }

    public PagedModel<PendingElectoralResult> getPendingElectoralResults(Pageable page) {
        UserDetails activeUser = AuthService.getActiveUser();
        Specification<PendingElectoralResult> spec = PendingElectoralResultSpecification
                .withUserIdentifier(activeUser.getUsername());
        return new PagedModel<>(this.pendingElectoralResultRepository.findAll(spec, page));
    }

    public void validateElectoralResult(ValidateElectoralResultRequest request) {
        this.electoralResultUploadService.checkResultValidity(request);
        this.electoralResultUploadService.updateResult(request.getResultId(), request.getBlankVotes(),
                request.getNullVotes(), request.getRegisteredVoters(), request.getResultDetails());
    }
}
