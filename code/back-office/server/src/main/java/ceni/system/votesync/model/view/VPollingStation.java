package ceni.system.votesync.model.view;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "liste_bv")
@Immutable
public class VPollingStation {

    @Id
    private Integer id;

    @Column(name = "code_bv")
    private String pollingStationCode;

    @Column(name = "nom_bv")
    private String pollingStation;

    @Column(name = "id_cv")
    private Integer votingCenterId;

    @Column(name = "nom_cv")
    private String votingCenter;

    @Column(name = "id_fokontany")
    private Integer fokontanyId;

    @Column(name = "nom_fokontany")
    private String fokontany;

    @Column(name = "id_commune")
    private Integer communeId;

    @Column(name = "nom_commune")
    private String commune;

    @Column(name = "id_district")
    private Integer districtId;

    @Column(name = "nom_district")
    private String district;

    @Column(name = "id_region")
    private Integer regionId;

    @Column(name = "nom_region")
    private String region;

    @Transient
    private Integer voters;
}
