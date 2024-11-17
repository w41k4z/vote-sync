package ceni.system.votesync.repository.entity.election.result;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.model.entity.election.result.ImportedResult;
import ceni.system.votesync.repository.base.EntityRepository;

public interface ImportedResultRepository extends EntityRepository<ImportedResult, Integer> {

    @Transactional
    @Procedure(procedureName = "import_electoral_results")
    void importElectoralResults(Integer electionId);
}
