package internship.project.election.model.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import org.springframework.data.annotation.Immutable;

@Data
@Entity
@Table(name = "regions")
@Immutable
public class Region {

    @Id
    private Integer id;

    @Column(name = "id_province")
    private Integer provinceId;

    @Column(name = "nom")
    private String name;

    private String geojson;
}
