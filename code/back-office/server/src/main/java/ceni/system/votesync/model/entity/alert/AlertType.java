package ceni.system.votesync.model.entity.alert;

import ceni.system.votesync.model.base.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "type_alertes")
public class AlertType extends AbstractEntity<Integer> {

    public final static Integer SUSPECT_REGISTRATION = 1;
    public final static Integer UNSYNCED_DATA = 2;
    public final static Integer INCOHERENT_DATA = 3;

    @Column(name = "nom")
    private String name;

    @Column(name = "niveau")
    private Integer level;
}
