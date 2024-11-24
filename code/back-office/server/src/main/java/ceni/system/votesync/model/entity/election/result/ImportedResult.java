package ceni.system.votesync.model.entity.election.result;

import ceni.system.votesync.model.base.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resultats_importes")
public class ImportedResult extends AbstractEntity<Integer> {

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "code_bv")
    private String pollingStationCode;

    @Column(name = "inscrits")
    private Integer voters;

    @Transient
    private Integer registeredVoters;

    @Column(name = "homme_moins_36")
    private Integer maleUnder36;

    @Column(name = "femme_moins_36")
    private Integer femaleUnder36;

    @Column(name = "homme_36_plus")
    private Integer male36AndOver;

    @Column(name = "femme_36_plus")
    private Integer female36AndOver;

    @Column(name = "handicapes")
    private Integer disabledPeople;

    @Column(name = "malvoyants")
    private Integer visuallyImpairedPeople;

    @Column(name = "blancs")
    private Integer blankVotes;

    @Column(name = "nuls")
    private Integer nullVotes;
}
