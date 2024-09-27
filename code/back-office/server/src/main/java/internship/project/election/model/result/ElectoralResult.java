package internship.project.election.model.result;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ElectoralResult {

    @Id
    private Integer id;

    private String code;

    @Column(name = "nom")
    private String name;
}
