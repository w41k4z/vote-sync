package ceni.system.votesync.repository.entity.result;

import org.springframework.data.jpa.repository.query.Procedure;

import ceni.system.votesync.model.entity.election.result.ImportedResultDetails;
import ceni.system.votesync.repository.base.EntityRepository;

public interface ElectoralResultDetailsUploadRepository extends EntityRepository<ImportedResultDetails, Integer> {

    @Procedure(procedureName = "import_electoral_result_details")
    void importElectoralResultDetails();
}
