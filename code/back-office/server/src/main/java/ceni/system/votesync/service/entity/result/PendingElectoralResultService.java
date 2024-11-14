package ceni.system.votesync.service.entity.result;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ceni.system.votesync.model.view.election.result.PendingElectoralResult;
import ceni.system.votesync.repository.view.election.result.PendingElectoralResultRepository;
import ceni.system.votesync.service.spec.auth.AuthService;

@Service
public class PendingElectoralResultService {

    private PendingElectoralResultRepository pendingElectoralResultRepository;

    public PendingElectoralResultService(PendingElectoralResultRepository pendingElectoralResultRepository) {
        this.pendingElectoralResultRepository = pendingElectoralResultRepository;
    }

    public PagedModel<PendingElectoralResult> getPendingElectoralResults(Pageable page) {
        UserDetails activeUser = AuthService.getActiveUser();
        Specification<PendingElectoralResult> spec = PendingElectoralResultSpecification
                .withUserIdentifier(activeUser.getUsername());
        return new PagedModel<>(this.pendingElectoralResultRepository.findAll(spec, page));
    }
}
