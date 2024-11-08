package ceni.system.votesync.model.view.election.result;

import java.util.List;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.entity.election.result.ResultImage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resultats_en_attente")
@Immutable
public class PendingElectoralResult {

    @Id
    private Integer id;

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "nom_election")
    private String election;

    @Column(name = "id_bv")
    private Integer pollingStationId;

    @Column(name = "nom_bv")
    private String pollingStation;

    @Column(name = "identifiant_operateur_validateur")
    private String operatorIdentifier;

    @Column(name = "inscrits")
    private Integer registeredVoters;

    @Column(name = "blancs")
    private Integer blankVotes;

    @Column(name = "nuls")
    private Integer nullVotes;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_resultat")
    private List<VResultDetails> details;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_resultat")
    private List<ResultImage> images;

    @Column(name = "etat")
    private Integer status;
}
