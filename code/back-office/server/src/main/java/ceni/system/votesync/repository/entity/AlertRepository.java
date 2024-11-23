package ceni.system.votesync.repository.entity;

import java.sql.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ceni.system.votesync.model.entity.alert.Alert;
import ceni.system.votesync.repository.base.EntityRepository;

public interface AlertRepository extends EntityRepository<Alert, Integer> {

    @Transactional
    @Procedure(procedureName = "import_alerts")
    void importAlerts();

    @Modifying
    @Transactional
    @Query(value = "UPDATE alertes SET etat = :status WHERE id = :id", nativeQuery = true)
    int updateAlertStatus(
            @Param("status") Integer status,
            @Param("id") Integer id);

    @Query(value = "INSERT INTO alertes(id_type_alerte, id_election, id_bv, date_alerte, description, etat) VALUES(:alertTypeId, :electionId, :pollingStationId, :alertDate, :description, :status)", nativeQuery = true)
    void createAlert(@Param("alertTypeId") Integer alertTypeId, @Param("electionId") Integer electionId,
            @Param("pollingStationId") Integer pollingStationId, @Param("alertDate") Date alertDate,
            @Param("description") String description, @Param("status") Integer status);
}
