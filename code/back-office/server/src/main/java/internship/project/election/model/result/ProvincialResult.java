package internship.project.election.model.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import internship.project.election.model.result.details.ProvincialResultDetails;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
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
@Table(name = "provinces")
@Immutable
@AttributeOverride(name = "code", column = @Column(name = "id", insertable = false, updatable = false))
public class ProvincialResult extends ElectoralResult {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_province")
    private List<ProvincialResultDetails> details;
}
