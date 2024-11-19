package ceni.system.votesync.repository.base.entity.election.result.provisional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import ceni.system.votesync.model.base.entity.election.result.provisional.ProvisionalElectoralResult;
import ceni.system.votesync.repository.base.EntityRepository;

@NoRepositoryBean
public interface ProvisionalElectoralResultRepository<T extends ProvisionalElectoralResult>
                extends EntityRepository<T, Integer> {

        @Query("SELECT per FROM #{#entityName} per JOIN Election e ON per.electionId = e.id WHERE per.divisionId = :divisionId AND e.type.id = :electionTypeId")
        Page<T> getDetailsWithElectionTypeId(Integer divisionId, Integer electionTypeId, Pageable pageable);

        @Query("SELECT per FROM #{#entityName} per JOIN Election e ON per.electionId = e.id WHERE per.divisionId = :divisionId")
        Page<T> getDetails(Integer divisionId, Pageable pageable);
}
