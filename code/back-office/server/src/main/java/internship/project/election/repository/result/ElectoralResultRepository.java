package internship.project.election.repository.result;

import internship.project.election.model.result.ElectoralResult;
import internship.project.election.repository.EntityRepository;

public interface ElectoralResultRepository<T extends ElectoralResult> extends EntityRepository<T, Integer> {
}
