package ceni.system.votesync.model.view.result.details;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.details.ElectoralResultDetails;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultats_par_region")
@AttributeOverrides({
                @AttributeOverride(name = "code", column = @Column(name = "code_region")),
                @AttributeOverride(name = "locationId", column = @Column(name = "id_region")),
                @AttributeOverride(name = "location", column = @Column(name = "nom_region"))
})
@Immutable
public class RegionalResultDetails extends ElectoralResultDetails {
}
