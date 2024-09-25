package internship.project.election.model.result.stat;

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
public class DistrictResultStat extends ElectoralResultStat {
}
