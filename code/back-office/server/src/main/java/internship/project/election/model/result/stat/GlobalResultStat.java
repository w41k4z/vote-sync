package internship.project.election.model.result.stat;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultats_election")
@AttributeOverrides({
                @AttributeOverride(name = "locationId", column = @Column(name = "id_pays")),
                @AttributeOverride(name = "localtion", column = @Column(name = "pays"))
})
public class GlobalResultStat extends ElectoralResultStat {
}
