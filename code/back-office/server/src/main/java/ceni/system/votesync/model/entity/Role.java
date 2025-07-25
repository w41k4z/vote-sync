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
@Table(name = "roles")
public class Role extends AbstractEntity<Integer> {

    @Column(name = "nom")
    private String name;

    @Column(name = "niveau")
    private Integer level;
}
