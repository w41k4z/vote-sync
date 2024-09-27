package internship.project.election.model.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import internship.project.election.model.result.details.DistrictResultDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "districts")
@Immutable
public class DistrictResult extends ElectoralResult {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_district")
    private List<DistrictResultDetails> details;
}
