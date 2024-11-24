package ceni.system.votesync.repository.entity;

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
}
