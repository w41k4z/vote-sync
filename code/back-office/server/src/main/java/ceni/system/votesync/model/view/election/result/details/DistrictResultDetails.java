package ceni.system.votesync.model.view.election.result.details;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.details.ElectoralResultDetails;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultats_par_district")
@AttributeOverrides({
        @AttributeOverride(name = "code", column = @Column(name = "code_district")),
        @AttributeOverride(name = "locationId", column = @Column(name = "id_district")),
        @AttributeOverride(name = "location", column = @Column(name = "nom_district"))
})
@Immutable
public class DistrictResultDetails extends ElectoralResultDetails {
}
