package ceni.system.votesync.model.entity.election.result.provisional.details;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.entity.election.result.provisional.details.ProvisionalElectoralResultDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fokontany_details_resultats_provisoires")
@Immutable
public class ProvisionalFokontanyResultDetails extends ProvisionalElectoralResultDetails {
}
