package ceni.system.votesync.model.entity.election.result;

import ceni.system.votesync.config.Status;
import ceni.system.votesync.dto.request.result.UploadElectoralResultRequest;
import ceni.system.votesync.model.base.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resultats")
public class Result extends AbstractEntity<Integer> {

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "id_bv")
    private Integer pollingStationId;

    @Column(name = "inscrits")
    private Integer registeredVoters;

    @Column(name = "blancs")
    private Integer blankVotes;

    @Column(name = "nuls")
    private Integer nullVotes;

    @Column(name = "etat")
    private Integer status;

    public static Result fromUploadElectoralResultRequest(UploadElectoralResultRequest request) {
        Result result = new Result();
        result.setElectionId(request.getElectionId());
        result.setPollingStationId(request.getPollingStationId());
        result.setRegisteredVoters(request.getRegistered());
        result.setBlankVotes(request.getBlanks());
        result.setNullVotes(request.getNulls());
        result.setStatus(Status.PENDING);
        return result;
    }
}
