package ceni.system.votesync.repository.base.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.model.base.entity.location.AdministrativeDivision;
import ceni.system.votesync.repository.base.EntityRepository;

@NoRepositoryBean
public interface AdministrativeDivisionRepository<T extends AdministrativeDivision>
        extends EntityRepository<T, Integer> {

    @Query("SELECT new ceni.system.votesync.model.base.entity.location.AdministrativeDivision(a.id, a.name, a.code, a.upperDivisionId) FROM #{#entityName} a")
    List<T> findAllWithoutGeoJson();

    @Query("SELECT new ceni.system.votesync.model.base.entity.location.AdministrativeDivision(a.id, a.name, a.code, a.upperDivisionId) FROM #{#entityName} a WHERE a.upperDivisionId = ?1")
    List<T> findByUpperDivisionIdWithoutGeoJson(Integer upperDivisionId);
}
