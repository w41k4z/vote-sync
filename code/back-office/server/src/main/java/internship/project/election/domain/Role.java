package internship.project.election.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity<Integer> {

    @Column(name = "nom")
    private String name;

    @Column(name = "niveau")
    private Integer level;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}
