package internship.project.election.domain;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "jeton_rafrachissements")
public class RefreshToken {

    @Id
    @Column(name = "identifiant_utilisateur")
    private String userIdentifier;

    @Column(name = "jeton")
    private String token;
}
