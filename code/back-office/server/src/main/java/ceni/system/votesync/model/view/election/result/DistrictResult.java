package ceni.system.votesync.model.view.election.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.ElectoralResult;
import ceni.system.votesync.model.view.election.result.details.DistrictResultDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "resultat_statistique_par_district")
@Immutable
public class DistrictResult extends ElectoralResult {

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
    @JoinColumns({
            @JoinColumn(name = "id_district", referencedColumnName = "id"),
            @JoinColumn(name = "id_election", referencedColumnName = "id_election")
    })
    private List<DistrictResultDetails> details;
}
