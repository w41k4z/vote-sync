package ceni.system.votesync.repository.entity.election.result;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import ceni.system.votesync.model.entity.election.result.ImportedResultDetails;
import ceni.system.votesync.repository.base.EntityRepository;

public interface ImportedResultDetailsRepository extends EntityRepository<ImportedResultDetails, Integer> {

    @Procedure(procedureName = "import_electoral_result_details")
    void importElectoralResultDetails(Integer electionId);

    @Modifying
    @Query(value = "UPDATE details_resultats SET voix = :votes WHERE id = :resultDetailId", nativeQuery = true)
    int updateById(@Param("resultDetailId") Integer resultDetailId, @Param("votes") Integer votes);
}
