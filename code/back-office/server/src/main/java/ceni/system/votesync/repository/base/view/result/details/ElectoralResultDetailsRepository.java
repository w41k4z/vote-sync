package ceni.system.votesync.repository.base.view.result.details;

import ceni.system.votesync.model.base.view.result.details.ElectoralResultDetails;
import ceni.system.votesync.repository.base.EntityRepository;

public interface ElectoralResultDetailsRepository<T extends ElectoralResultDetails>
                extends EntityRepository<T, Integer> {
}
