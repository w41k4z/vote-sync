package ceni.system.votesync.model.entity.election.result;

import java.util.List;

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
@Table(name = "details_resultats_importes")
public class ImportedResultDetails extends AbstractEntity<Integer> {

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "code_bv")
    private String pollingStationCode;

    @Column(name = "numero_candidat")
    private Integer candidateNumber;

    @Column(name = "voix")
    private Integer votes;

    public static List<ImportedResultDetails> fromUploadElectoralResultRequestAndResultId(
            UploadElectoralResultRequest request) {
        return request.getCandidates().entrySet().stream().map(entry -> {
            ImportedResultDetails details = new ImportedResultDetails();
            details.setElectionId(request.getElectionId());
            details.setPollingStationCode(request.getPollingStationCode());
            details.setCandidateNumber(entry.getKey());
            details.setVotes(entry.getValue());
            return details;
        }).toList();
    }
}
