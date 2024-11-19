package ceni.system.votesync.model.entity.election.result.provisional;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.entity.election.result.provisional.ProvisionalElectoralResult;
import ceni.system.votesync.model.entity.election.result.provisional.details.ProvisionalMunicipalResultDetails;
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
@Table(name = "municipalites_resultats_provisoires")
@AttributeOverrides({
        @AttributeOverride(name = "divisionId", column = @Column(name = "id_municipalite")),
        @AttributeOverride(name = "name", column = @Column(name = "nom_municipalite")),
        @AttributeOverride(name = "code", column = @Column(name = "code_municipalite"))
})
@Immutable
public class ProvisionalMunicipalResult extends ProvisionalElectoralResult {

    @Column(name = "id_district")
    private Integer districtId;

    @Column(name = "nom_district")
    private String district;

    @Column(name = "id_region")
    private Integer regionId;

    @Column(name = "nom_region")
    private String region;

    @Column(name = "importes")
    private Integer importedResults;

    @Column(name = "nombre_bv")
    private Integer collectedResults;

    @Column(name = "nombre_total_bv")
    private Integer totalPollingStationCount;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_resultat_provisoire")
    private List<ProvisionalMunicipalResultDetails> details;
}
