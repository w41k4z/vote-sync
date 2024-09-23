package internship.project.election.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "type_elections")
public class ElectionType {

    @Id
    private Integer id;

    @Column(name = "nom")
    private String type;
}
