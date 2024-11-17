package ceni.system.votesync.model.base.entity.election.result.provisional;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ProvisionalElectoralResult {

    @Id
    private Integer id;

    @Column(name = "id_election")
    private Integer electionId;

    private Integer divisionId;

    private String code;

    private String name;

    @Column(name = "inscrits")
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

    @Column(name = "exprimes")
    private Integer validVotes;

    @Column(name = "nombre_alertes")
    private Integer alerts;
}
