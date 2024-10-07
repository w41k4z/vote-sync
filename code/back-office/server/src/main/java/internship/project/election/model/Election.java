package internship.project.election.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "elections")
public class Election extends AbstractEntity<Integer> {

    @OneToOne
    @JoinColumn(name = "id_type_election")
    private ElectionType type;

    @Column(name = "nom")
    private String name;

    @Column(name = "date_debut")
    private Date startDate;

    @Column(name = "date_fin")
    private Date endDate;

    @Column(name = "etat")
    private Integer state;

    @Transient
    private Integer candidates;

    @Transient
    private Integer voters;
}
