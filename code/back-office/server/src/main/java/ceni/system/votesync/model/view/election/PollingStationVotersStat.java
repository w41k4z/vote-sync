package ceni.system.votesync.model.view.election;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stat_electeur_par_bv")
@Immutable
public class PollingStationVotersStat {

    @Id
    @Column(name = "num_ligne")
    private Integer rowNumber;

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "id_bv")
    private Integer pollingStationId;

    @Column(name = "inscrits")
    private Integer voters;

    @Column(name = "homme_moins_36")
    private Integer maleUnder36;

    @Column(name = "femme_moins_36")
    private Integer femaleUnder36;

    @Column(name = "homme_36_plus")
    private Integer male36AndOver;

    @Column(name = "femme_36_plus")
    private Integer female36AndOver;

    @Column(name = "handicapes")
    private Integer disabledPeople;

    @Column(name = "malvoyants")
    private Integer visuallyImpairedPeople;
}