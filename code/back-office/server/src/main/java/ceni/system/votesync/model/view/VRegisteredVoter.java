package ceni.system.votesync.model.view;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "electeurs_inscrits")
@Immutable
public class VRegisteredVoter {

    @Id
    private Integer id;

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "id_bv")
    private Integer pollingStationId;

    @Column(name = "cin")
    private String nic;

    @Column(name = "nom")
    private String name;

    @Column(name = "prenom")
    private String firstName;

    @Column(name = "genre")
    private Integer gender;
}
