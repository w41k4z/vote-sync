package ceni.system.votesync.model.view.election;

import java.sql.Date;

import org.springframework.data.annotation.Immutable;

import ceni.system.votesync.model.entity.election.ElectionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "v_elections")
@Immutable
public class VElection {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_type_election")
    private ElectionType type;

    @Column(name = "nom")
    private String name;

    @Column(name = "date_debut")
    private Date startDate;

    @Column(name = "date_fin")
    private Date endDate;

    @Column(name = "nombre_bv")
    private Integer pollingStationCount;

    @Column(name = "nombre_total_bv")
    private Integer totalPollingStationCount;

    @Column(name = "etat")
    private Integer status;
}
