package ceni.system.votesync.model.view.result.details;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.details.ElectoralResultDetails;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultats_par_municipalite")
@AttributeOverrides({
                @AttributeOverride(name = "code", column = @Column(name = "code_municipalite")),
                @AttributeOverride(name = "locationId", column = @Column(name = "id_municipalite")),
                @AttributeOverride(name = "location", column = @Column(name = "nom_municipalite"))
})
@Immutable
public class MunicipalResultDetails extends ElectoralResultDetails {
}
