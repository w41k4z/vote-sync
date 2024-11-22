package ceni.system.votesync.model.view.election.result;

import lombok.Data;

import org.springframework.data.annotation.Immutable;

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
@Table(name = "v_details_resultats")
@Immutable
public class VResultDetails {

    @Id
    private Integer id;

    @Column(name = "id_resultat")
    private Integer resultId;

    @Column(name = "id_enregistrement_candidat")
    private Integer candidateRegistrationId;

    @Column(name = "voix")
    private Integer votes;

    @Column(name = "id_candidat")
    private Integer candidateId;

    @Column(name = "information_candidat")
    private String candidateInformation;

    @Column(name = "chemin_photo")
    private String candidateImagePath;

    @Column(name = "id_entite_politique")
    private Integer politicalEntityId;

    @Column(name = "nom_entite_politique")
    private String politicalEntity;

    @Column(name = "description_entite_politique")
    private String politicalEntityDescription;
}
