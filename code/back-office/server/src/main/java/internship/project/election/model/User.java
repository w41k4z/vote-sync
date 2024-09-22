package internship.project.election.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "utilisateurs")
public class User extends AbstractEntity<Integer> {

    @Column(name = "identifiant")
    private String identifier;

    @OneToOne
    @JoinColumn(name = "id_role")
    private Role role;

    @Column(name = "nom")
    private String name;

    @Column(name = "prenom")
    private String firstName;

    private String contact;

    @Column(name = "mot_de_passe")
    private String password;

    @Column(name = "etat")
    private Integer state;

    // Ignore this field, it is used for search purposes because
    // JPA specification does not support direct access to database table columns
    @Column(name = "search_column")
    private String searchColumn;
}
