package internship.project.election.model.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import internship.project.election.model.result.details.FokontanyResultDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "fokontany_resultats")
@Immutable
public class FokontanyResult extends ElectoralResult {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "id_fokontany", referencedColumnName = "id"),
            @JoinColumn(name = "id_election", referencedColumnName = "id_election")
    })
    private List<FokontanyResultDetails> details;
}
