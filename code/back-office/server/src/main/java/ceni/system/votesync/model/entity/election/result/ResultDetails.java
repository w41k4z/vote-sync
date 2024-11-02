package ceni.system.votesync.model.entity.election.result;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "details_resultats")
public class ResultDetails {

    @Id
    private Integer id;

    @Column(name = "id_resultat")
    private Integer resultId;

    @Column(name = "id_enregistrement_candidat")
    private Integer candidateRegistrationId;

    @Column(name = "voix")
    private Integer votes;
}
