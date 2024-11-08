package ceni.system.votesync.model.view.election.result.details;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.result.details.ElectoralResultDetails;
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
@Immutable
public class GlobalResultDetails extends ElectoralResultDetails {
}
