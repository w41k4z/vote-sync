package ceni.system.votesync.model.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.result.details.MunicipalResultDetails;
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
@Table(name = "municipalites_resultats")
@Immutable
public class MunicipalResult extends ElectoralResult {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "id_municipalite", referencedColumnName = "id"),
            @JoinColumn(name = "id_election", referencedColumnName = "id_election")
    })
    private List<MunicipalResultDetails> details;
}
