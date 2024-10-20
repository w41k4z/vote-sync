package ceni.system.votesync.repository.base.view.result;

import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.repository.base.EntityRepository;
import ceni.system.votesync.model.base.view.result.ElectoralResult;

@NoRepositoryBean
public interface ElectoralResultRepository<T extends ElectoralResult> extends EntityRepository<T, Integer> {
}
