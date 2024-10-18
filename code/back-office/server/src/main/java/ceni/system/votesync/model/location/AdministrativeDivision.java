package ceni.system.votesync.model.location;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class AdministrativeDivision {

    @Id
    private Integer id;

    @Column(name = "nom")
    private String name;

    private String code;

    private String geojson;

    private Integer upperDivisionId;

    public AdministrativeDivision() {
    }

    public AdministrativeDivision(Integer id, String name, Integer upperDivisionId) {
        this.id = id;
        this.name = name;
        this.upperDivisionId = upperDivisionId;
    }

    public AdministrativeDivision(Integer id, String name, Integer upperDivisionId, String geoJson) {
        this(id, name, upperDivisionId);
        this.geojson = geoJson;
    }
}
