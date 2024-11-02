package ceni.system.votesync.model.entity.election.result;

import ceni.system.votesync.model.base.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resultat_images")
public class ResultImage extends AbstractEntity<Integer> {

    @Column(name = "id_resultat")
    private Integer resultId;

    @Column(name = "chemin_image")
    private String imagePath;
}
