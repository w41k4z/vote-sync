package ceni.system.votesync.model.location;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "communes")
@AttributeOverride(name = "upperDivisionId", column = @Column(name = "id_district"))
public class Commune extends AdministrativeDivision {
}
