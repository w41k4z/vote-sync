package ceni.system.votesync.model.view.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.ElectoralResult;
import ceni.system.votesync.model.view.result.details.RegionalResultDetails;

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
@Table(name = "regions_resultats")
@Immutable
public class RegionalResult extends ElectoralResult {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "id_region", referencedColumnName = "id"),
            @JoinColumn(name = "id_election", referencedColumnName = "id_election")
    })
    private List<RegionalResultDetails> details;
}
