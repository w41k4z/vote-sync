package ceni.system.votesync.repository.base.entity.election.result.provisional;

import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.model.base.entity.election.result.provisional.ProvisionalElectoralResult;
import ceni.system.votesync.repository.base.EntityRepository;

@NoRepositoryBean
public interface ProvisionalElectoralResultRepository<T extends ProvisionalElectoralResult>
        extends EntityRepository<T, Integer> {
}
