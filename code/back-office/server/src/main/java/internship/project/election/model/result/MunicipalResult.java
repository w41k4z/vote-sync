package internship.project.election.model.result;

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
public class MunicipalResult extends ElectoralResult {
}
