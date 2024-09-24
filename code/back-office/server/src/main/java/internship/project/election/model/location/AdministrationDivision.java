package internship.project.election.model.location;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AdministrationDivision {

    @Id
    private Integer id;

    @Column(name = "nom")
    private String name;

    private String geojson;

    private Integer upperDivisionId;
}
