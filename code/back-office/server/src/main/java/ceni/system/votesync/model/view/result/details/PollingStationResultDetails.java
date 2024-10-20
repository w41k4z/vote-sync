package ceni.system.votesync.model.view.result.details;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.details.ElectoralResultDetails;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultats_par_bv")
@AttributeOverrides({
                @AttributeOverride(name = "code", column = @Column(name = "code_bv")),
                @AttributeOverride(name = "locationId", column = @Column(name = "id_bv")),
                @AttributeOverride(name = "location", column = @Column(name = "nom_bv"))
})
@Immutable
public class PollingStationResultDetails extends ElectoralResultDetails {
}
