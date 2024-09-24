package internship.project.election.model.location;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class AdministrationDivision {

    @Id
    private Integer id;

    @Column(name = "nom")
    private String name;

    private String geojson;

    private Integer upperDivisionId;

    public AdministrationDivision() {
    }

    public AdministrationDivision(Integer id, String name, Integer upperDivisionId) {
        this.id = id;
        this.name = name;
        this.upperDivisionId = upperDivisionId;
    }
}
