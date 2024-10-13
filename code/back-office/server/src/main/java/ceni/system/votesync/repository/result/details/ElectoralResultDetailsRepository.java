package ceni.system.votesync.repository.result.details;

import ceni.system.votesync.model.result.details.ElectoralResultDetails;
import ceni.system.votesync.repository.EntityRepository;

public interface ElectoralResultDetailsRepository<T extends ElectoralResultDetails>
                extends EntityRepository<T, Integer> {
}
