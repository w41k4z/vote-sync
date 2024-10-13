package ceni.system.votesync.model.result.details;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ElectoralResultDetails {

    @Id
    private Integer id;

    @Column(name = "id_election")
    private Integer electionId;

    private String code;

    private Integer locationId;

    private String location;

    @Column(name = "id_candidat")
    private Integer candidateId;

    @Column(name = "numero_candidat")
    private Integer candidateNumber;

    @Column(name = "information_candidat")
    private String candidateInformation;

    @Column(name = "id_entite_politique")
    private Integer politicalEntityId;

    @Column(name = "nom_entite_politique")
    private String politicalEntity;

    @Column(name = "voix_candidat")
    private Integer candidateVotes;

    @Column(name = "chemin_photo_candidat")
    private String imagePath;
}
