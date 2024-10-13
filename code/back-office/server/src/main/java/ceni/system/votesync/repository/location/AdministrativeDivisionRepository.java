package ceni.system.votesync.repository.location;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.model.location.AdministrativeDivision;
import ceni.system.votesync.repository.EntityRepository;

@NoRepositoryBean
public interface AdministrativeDivisionRepository<T extends AdministrativeDivision>
        extends EntityRepository<T, Integer> {

    @Query("SELECT new internship.project.election.model.location.AdministrativeDivision(a.id, a.name, a.upperDivisionId) FROM #{#entityName} a")
    List<T> findAllWithoutGeoJson();

    @Query("SELECT new internship.project.election.model.location.AdministrativeDivision(a.id, a.name, a.upperDivisionId) FROM #{#entityName} a WHERE a.upperDivisionId = ?1")
    List<T> findByUpperDivisionIdWithoutGeoJson(Integer upperDivisionId);
}
