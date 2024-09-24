package internship.project.election.repository.location;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import internship.project.election.model.location.AdministrationDivision;
import internship.project.election.repository.EntityRepository;

@NoRepositoryBean
public interface AdministrativeDivisionRepository<T extends AdministrationDivision>
        extends EntityRepository<T, Integer> {

    @Query("SELECT new internship.project.election.model.location.AdministrationDivision(a.id, a.name, a.upperDivisionId) FROM #{#entityName} a")
    List<AdministrationDivision> findAllWithoutGeoJson();

    List<T> findByUpperDivisionId(Integer upperDivisionId);
}
