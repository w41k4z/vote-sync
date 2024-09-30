package internship.project.election.model.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import internship.project.election.model.result.details.PollingStationResultDetails;
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
@Table(name = "bv_resultats")
@Immutable
public class PollingStationResult extends ElectoralResult {

        @OneToMany(fetch = FetchType.EAGER)
        @JoinColumns({
                        @JoinColumn(name = "id_bv", referencedColumnName = "id"),
                        @JoinColumn(name = "id_election", referencedColumnName = "id_election")
        })
        private List<PollingStationResultDetails> details;
}
