package ceni.system.votesync.model.view.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.ElectoralResult;
import ceni.system.votesync.model.view.result.details.MunicipalResultDetails;

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
@Table(name = "municipalites_resultats")
@Immutable
public class MunicipalResult extends ElectoralResult {

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
            @JoinColumn(name = "id_municipalite", referencedColumnName = "id"),
            @JoinColumn(name = "id_election", referencedColumnName = "id_election")
    })
    private List<MunicipalResultDetails> details;
}
