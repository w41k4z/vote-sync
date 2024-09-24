package internship.project.election.model.view;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "liste_bv")
@Immutable
public class VPollingStation {

    @Id
    private Integer id;

    @Column(name = "code_bv")
    private String pollingStationCode;

    @Column(name = "nom_bv")
    private String pollingStation;

    @Column(name = "nom_cv")
    private String votingCenter;

    @Column(name = "nom_fokontany")
    private String fokontany;

    @Column(name = "nom_commune")
    private String commune;

    @Column(name = "nom_district")
    private String district;

    @Column(name = "nom_region")
    private String region;
}
