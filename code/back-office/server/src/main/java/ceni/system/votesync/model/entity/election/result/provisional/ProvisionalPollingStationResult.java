package ceni.system.votesync.model.entity.election.result.provisional;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.entity.election.result.provisional.ProvisionalElectoralResult;
import ceni.system.votesync.model.entity.election.result.provisional.details.ProvisionalPollingStationResultDetails;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "bv_resultats_provisoires")
@AttributeOverrides({
        @AttributeOverride(name = "divisionId", column = @Column(name = "id_bv")),
        @AttributeOverride(name = "name", column = @Column(name = "nom_bv")),
        @AttributeOverride(name = "code", column = @Column(name = "code_bv"))
})
@Immutable
public class ProvisionalPollingStationResult extends ProvisionalElectoralResult {

    @Column(name = "id_fokontany")
    private Integer fokontanyId;

    @Column(name = "nom_fokontany")
    private String fokontany;

    @Column(name = "id_commune")
    private Integer communeId;

    @Column(name = "nom_commune")
    private String commune;

    @Column(name = "id_municipalite")
    private Integer municipalityId;

    @Column(name = "nom_municipalite")
    private String municipality;

    @Column(name = "id_district")
    private Integer districtId;

    @Column(name = "nom_district")
    private String district;

    @Column(name = "id_district_municipal")
    private Integer municipalityDistrictId;

    @Column(name = "nom_district_municipal")
    private String municipalityDistrict;

    @Column(name = "id_region")
    private Integer regionId;

    @Column(name = "nom_region")
    private String region;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_resultat_provisoire")
    private List<ProvisionalPollingStationResultDetails> details;
}
