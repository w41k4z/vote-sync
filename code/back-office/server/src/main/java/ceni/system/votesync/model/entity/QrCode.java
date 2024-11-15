package ceni.system.votesync.model.entity;

import java.sql.Date;

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
@Table(name = "code_qr")
public class QrCode extends AbstractEntity<Integer> {

    @Column(name = "contenu")
    private String content;

    @Column(name = "date_creation")
    private Date creationDate;

    @Column(name = "date_expiration")
    private Date expirationDate;
}
