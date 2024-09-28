package internship.project.election.model;

import internship.project.election.config.ElectionTypeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "type_elections")
public class ElectionType {

    @Id
    private Integer id;

    @Column(name = "nom")
    private String type;

    public boolean isPresidential() {
        return this.id == ElectionTypeId.PRESIDENTIAL_ID;
    }

    public boolean isLegislative() {
        return this.id == ElectionTypeId.LEGISLATIVE_ID;
    }

    public boolean isLocal() {
        return this.id == ElectionTypeId.LOCAL_ID;
    }
}
