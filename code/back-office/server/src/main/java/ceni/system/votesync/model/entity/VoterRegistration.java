package ceni.system.votesync.model.entity;

import ceni.system.votesync.model.base.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "enregistrement_electeurs")
public class VoterRegistration extends AbstractEntity<Integer> {

    @Column(name = "id_election")
    private Integer electionId;

    @Column(name = "nom")
    private String name;

    @Column(name = "prenom")
    private String firstName;

    private Integer gender;
}
