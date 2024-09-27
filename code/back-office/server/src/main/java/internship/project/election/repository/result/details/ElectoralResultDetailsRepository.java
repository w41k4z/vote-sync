package internship.project.election.repository.result.details;

import internship.project.election.model.result.details.ElectoralResultDetails;
import internship.project.election.repository.EntityRepository;

public interface ElectoralResultDetailsRepository<T extends ElectoralResultDetails>
        extends EntityRepository<T, Integer> {
}
