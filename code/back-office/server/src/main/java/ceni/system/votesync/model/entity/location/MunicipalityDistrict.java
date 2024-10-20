package ceni.system.votesync.model.entity.location;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.entity.location.AdministrativeDivision;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "v_district_municipalites")
@AttributeOverride(name = "upperDivisionId", column = @Column(name = "id_region"))
@Immutable
public class MunicipalityDistrict extends AdministrativeDivision {
}
