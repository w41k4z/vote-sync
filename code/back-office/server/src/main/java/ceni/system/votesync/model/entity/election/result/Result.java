package ceni.system.votesync.model.entity.election.result;

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

    @Column(name = "importe")
    private Integer imported; // 0: via app, 1: via import

    @Column(name = "etat")
    private Integer status;

    public static Result fromUploadElectoralResultRequest(UploadElectoralResultRequest request) {
        Result result = new Result();
        result.setElectionId(request.getElectionId());
        result.setPollingStationId(request.getPollingStationId());
        result.setRegisteredVoters(request.getRegistered());
        result.setBlankVotes(request.getBlanks());
        result.setNullVotes(request.getNulls());
        return result;
    }
}
