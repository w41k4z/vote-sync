package ceni.system.votesync.repository.base.entity;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.model.base.entity.location.AdministrativeDivision;
import ceni.system.votesync.repository.base.EntityRepository;

@NoRepositoryBean
public interface AdministrativeDivisionRepository<T extends AdministrativeDivision>
        extends EntityRepository<T, Integer> {

    List<T> findByUpperDivisionId(Integer upperDivisionId);
}
