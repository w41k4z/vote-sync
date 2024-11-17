package ceni.system.votesync.model.base.entity.election.result.provisional.details;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ProvisionalElectoralResultDetails {

    @Id
    private Integer id;

    @Column(name = "id_resultat_provisoire")
    private Integer provisionalResultId;

    @Column(name = "numero_candidat")
    private Integer candidateNumber;

    @Column(name = "information_candidat")
    private String candidateInformation;

    @Column(name = "nom_entite_politique")
    private String politicalEntity;

    @Column(name = "description_entite_politique")
    private String politicalEntityDescription;

    @Column(name = "voix_candidat")
    private Integer candidateVotes;

    @Column(name = "chemin_photo_candidat")
    private String imagePath;
}
