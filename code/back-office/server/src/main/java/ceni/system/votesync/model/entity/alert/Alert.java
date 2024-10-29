package ceni.system.votesync.model.entity.alert;

import java.sql.Date;

import ceni.system.votesync.model.base.entity.AbstractEntity;
import ceni.system.votesync.model.view.VPollingStation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "alertes")
public class Alert extends AbstractEntity<Integer> {

    @OneToOne
    @JoinColumn(name = "id_type_alerte")
    private AlertType alertType;

    @Column(name = "id_election")
    private Integer electionId;

    @OneToOne
    @JoinColumn(name = "id_bv")
    private VPollingStation pollingStation;

    @Column(name = "date_alerte")
    private Date alertDate;

    private String description;

    @Column(name = "etat")
    private String status;
}
