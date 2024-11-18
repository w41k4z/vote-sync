package ceni.system.votesync.repository.base.entity.election.stat;

import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.model.base.view.stat.AdministrativeDivisionStat;
import ceni.system.votesync.repository.base.EntityRepository;

@NoRepositoryBean
public interface AdministrativeDivisionStatRepository<T extends AdministrativeDivisionStat>
        extends EntityRepository<T, Integer> {
}
