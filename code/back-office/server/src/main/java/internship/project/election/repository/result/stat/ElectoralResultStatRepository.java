package internship.project.election.repository.result.stat;

import internship.project.election.model.result.stat.ElectoralResultStat;
import internship.project.election.repository.EntityRepository;

public interface ElectoralResultStatRepository<T extends ElectoralResultStat> extends EntityRepository<T, Integer> {
}
