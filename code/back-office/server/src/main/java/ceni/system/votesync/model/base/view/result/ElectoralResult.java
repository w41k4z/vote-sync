package ceni.system.votesync.model.base.view.result;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ElectoralResult {

    @Id
    @Column(name = "num_ligne")
    private Integer rowNumber;

    private Integer id;

    @Column(name = "id_election")
    private Integer electionId;

    private String code;

    @Column(name = "nom")
    private String name;

    @Column(name = "inscrits")
    private Integer registeredVoters;

    @Column(name = "blancs")
    private Integer blankVotes;

    @Column(name = "nuls")
    private Integer nullVotes;

    @Column(name = "exprimes")
    private Integer validVotes;
}
