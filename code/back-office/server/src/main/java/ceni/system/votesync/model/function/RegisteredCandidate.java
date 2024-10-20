package ceni.system.votesync.model.function;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;

import lombok.Data;

@Data
@Entity
@Immutable
public class RegisteredCandidate {

    @Id
    private Integer id;

    @Column(name = "id_enregistrement")
    private Integer registrationId;

    @Column(name = "date_enregistrement")
    private Date registrationDate;

    @Column(name = "numero_candidat")
    private Integer candidateNumber;

    @Column(name = "information_candidat")
    private String information;

    @Column(name = "entite_politique")
    private String politicalEntity;

    @Column(name = "description_entite_politique")
    private String politicalEntityDescription;

    @Column(name = "chemin_photo")
    private String imagePath;
}
