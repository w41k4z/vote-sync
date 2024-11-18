package ceni.system.votesync.model.view.election.stat;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.base.view.stat.AdministrativeDivisionStat;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "stat_commune")
@AttributeOverrides({
        @AttributeOverride(name = "divisionId", column = @Column(name = "id_commune")),
        @AttributeOverride(name = "divisionName", column = @Column(name = "commune")),
})
@Immutable
public class CommunalStat extends AdministrativeDivisionStat {
}
