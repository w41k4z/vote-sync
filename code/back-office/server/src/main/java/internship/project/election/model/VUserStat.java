package internship.project.election.model;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stat_utilisateur")
@Immutable
public class VUserStat {

    @Id
    @Column(name = "id_role")
    private Integer id;

    @Column(name = "nom_role")
    private String role;

    @Column(name = "nombre_utilisateur")
    private Integer count;
}
