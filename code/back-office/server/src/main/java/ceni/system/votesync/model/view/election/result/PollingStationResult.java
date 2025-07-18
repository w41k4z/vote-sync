package ceni.system.votesync.model.view.election.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.ElectoralResult;
import ceni.system.votesync.model.view.election.result.details.PollingStationResultDetails;
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
@Table(name = "resultat_statistique_par_bv")
@Immutable
public class PollingStationResult extends ElectoralResult {

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

        @OneToMany(fetch = FetchType.EAGER)
        @JoinColumns({
                        @JoinColumn(name = "id_bv", referencedColumnName = "id"),
                        @JoinColumn(name = "id_election", referencedColumnName = "id_election")
        })
        private List<PollingStationResultDetails> details;
}
