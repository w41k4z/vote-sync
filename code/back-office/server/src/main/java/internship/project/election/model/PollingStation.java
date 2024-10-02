package internship.project.election.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "bv")
public class PollingStation extends AbstractEntity<Integer> {

    private String code;

    @Column(name = "id_cv")
    private Integer voteCenterId;

    @Column(name = "nom")
    private String name;
}
