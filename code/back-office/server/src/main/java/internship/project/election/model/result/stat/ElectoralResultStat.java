package internship.project.election.model.result.stat;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ElectoralResultStat {

    @Id
    private Integer id;

    @Column(name = "id_election")
    private Integer electionId;

    private Integer locationId;

    private String location;

    private String code;

    @Column(name = "inscrits")
    private Integer registeredVoters;

    @Column(name = "blancs")
    private Integer blankVotes;

    @Column(name = "nuls")
    private Integer nullVotes;

    @Column(name = "exprimes")
    private Integer validVotes;
}
