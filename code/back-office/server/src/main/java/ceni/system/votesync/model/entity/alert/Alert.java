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
@Table(name = "alertes_importees")
public class Alert extends AbstractEntity<Integer> {

    @Column(name = "id_type_alerte")
    private Integer alertTypeId;

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "code_bv")
    private String pollingStationCode;

    @Column(name = "date_alerte")
    private Date alertDate;

    private String description;

    @Column(name = "etat")
    private Integer status;
}
