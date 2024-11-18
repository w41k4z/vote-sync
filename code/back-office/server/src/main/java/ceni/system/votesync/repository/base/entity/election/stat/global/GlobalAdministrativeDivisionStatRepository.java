package ceni.system.votesync.repository.base.entity.election.stat.global;

import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.model.base.view.stat.global.GlobalAdministrativeDivisionStat;
import ceni.system.votesync.repository.base.EntityRepository;

@NoRepositoryBean
public interface GlobalAdministrativeDivisionStatRepository<T extends GlobalAdministrativeDivisionStat>
        extends EntityRepository<T, Integer> {
}
