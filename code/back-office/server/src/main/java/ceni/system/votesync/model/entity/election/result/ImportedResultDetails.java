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

    @Column(name = "id_resultat")
    private Integer resultId;

    @Column(name = "id_candidat")
    private Integer candidateId;

    @Column(name = "voix")
    private Integer votes;

    public static List<ImportedResultDetails> fromUploadElectoralResultRequestAndResultId(
            UploadElectoralResultRequest request,
            int resultId) {
        return request.getCandidates().entrySet().stream().map(entry -> {
            ImportedResultDetails details = new ImportedResultDetails();
            details.setResultId(resultId);
            details.setCandidateId(entry.getKey());
            details.setVotes(entry.getValue());
            return details;
        }).toList();
    }
}
