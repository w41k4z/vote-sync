package ceni.system.votesync.model.entity.alert;

import java.sql.Date;

import ceni.system.votesync.model.base.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "alertes")
public class BaseAlert extends AbstractEntity<Integer> {

    @Column(name = "id_type_alerte")
    private Integer alertTypeId;

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "id_bv")
    private Integer pollingStationId;

    @Column(name = "date_alerte")
    private Date alertDate;

    private String description;

    @Column(name = "etat")
    private Integer status;
}
