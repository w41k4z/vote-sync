package ceni.system.votesync.model.view;

import java.sql.Date;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "v_alertes")
@Immutable
public class VAlert {

    @Id
    private Integer id;

    @Column(name = "id_type_alerte")
    private Integer alertTypeId;

    @Column(name = "nom_type_alerte")
    private String alertType;

    @Column(name = "niveau_type_alerte")
    private Integer alertLevel;

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "nom_election")
    private String election;

    @Column(name = "id_bv")
    private Integer pollingStationId;

    @Column(name = "nom_bv")
    private String pollingStation;

    @Column(name = "id_fokontany")
    private Integer fokontanyId;

    @Column(name = "id_commune")
    private Integer communeId;

    @Column(name = "id_district")
    private Integer districtId;

    @Column(name = "id_region")
    private Integer regionId;

    @Column(name = "date_alerte")
    private Date alertDate;

    private String description;

    @Column(name = "etat")
    private Integer status;
}
