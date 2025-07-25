package ceni.system.votesync.model.base.view.stat.global;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class GlobalAdministrativeDivisionStat {

    @Id
    @Column(name = "num_ligne")
    private Integer rowNumber;

    @Column(name = "inscrits")
    private Integer voters;

    @Column(name = "enregistres")
    private Integer registered;

    @Column(name = "importes")
    private Integer importedResults;

    @Column(name = "nombre_bv")
    private Integer collectedResults;

    @Column(name = "nombre_total_bv")
    private Integer totalPollingStationCount;

    @Column(name = "nombre_alertes")
    private Integer alerts;

    private Integer divisionId;

    private String divisionName;

    private String geojson;
}
