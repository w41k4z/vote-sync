package internship.project.election.model.result.details;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultats_par_commune")
@AttributeOverrides({
                @AttributeOverride(name = "code", column = @Column(name = "code_commune")),
                @AttributeOverride(name = "locationId", column = @Column(name = "id_commune")),
                @AttributeOverride(name = "location", column = @Column(name = "nom_commune"))
})
public class CommunalResultDetails extends ElectoralResultDetails {
}
