package ceni.system.votesync.model.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.result.details.CommunalResultDetails;
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
@Table(name = "communes_resultats")
@Immutable
public class CommunalResult extends ElectoralResult {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "id_commune", referencedColumnName = "id"),
            @JoinColumn(name = "id_election", referencedColumnName = "id_election")
    })
    private List<CommunalResultDetails> details;
}
