package ceni.system.votesync.repository.entity.election.result;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.model.entity.election.result.Result;
import ceni.system.votesync.repository.base.EntityRepository;

public interface ElectoralResultUploadRepository extends EntityRepository<Result, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE resultats SET etat = :status WHERE id_election = :electionId AND id_bv = :pollingStationId", nativeQuery = true)
    int updateResultState(
            @Param("status") Integer status,
            @Param("electionId") Integer electionId,
            @Param("pollingStationId") Integer pollingStationId);
}
