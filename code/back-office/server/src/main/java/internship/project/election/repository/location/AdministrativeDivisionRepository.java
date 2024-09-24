package internship.project.election.repository.location;

import org.springframework.data.repository.NoRepositoryBean;

import internship.project.election.model.location.AdministrationDivision;
import internship.project.election.repository.EntityRepository;

@NoRepositoryBean
public interface AdministrativeDivisionRepository<T extends AdministrationDivision>
                extends EntityRepository<T, Integer> {

}
