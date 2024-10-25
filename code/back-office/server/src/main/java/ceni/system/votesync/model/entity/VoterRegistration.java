package ceni.system.votesync.model.entity;

import java.sql.Timestamp;

import ceni.system.votesync.model.base.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "enregistrement_electeurs")
public class VoterRegistration extends AbstractEntity<Integer> {

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "id_bv")
    private Integer pollingStationId;

    @Column(name = "id_electeur")
    private Integer voterId;

    @Column(name = "vote")
    private Integer hasVoted;

    @Column(name = "date_enregistrement")
    private Timestamp registrationDate;
}
