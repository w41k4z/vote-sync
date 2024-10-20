package ceni.system.votesync.model.entity.location;

import ceni.system.votesync.model.base.entity.location.AdministrativeDivision;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "regions")
@AttributeOverride(name = "upperDivisionId", column = @Column(name = "id_province"))
public class Region extends AdministrativeDivision {
}
