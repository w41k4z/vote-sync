package internship.project.election.model.location;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "districts")
@AttributeOverride(name = "upperDivisionId", column = @Column(name = "id_region"))
public class District extends AdministrativeDivision {
}
