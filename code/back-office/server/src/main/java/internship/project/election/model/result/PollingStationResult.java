package internship.project.election.model.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import internship.project.election.model.result.details.PollingStationResultDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "bv")
@Immutable
public class PollingStationResult extends ElectoralResult {

        @OneToMany(fetch = FetchType.EAGER)
        @JoinColumn(name = "id_bv")
        private List<PollingStationResultDetails> details;
}
