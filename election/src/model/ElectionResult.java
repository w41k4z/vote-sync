package model;

import orm.annotation.Column;
import orm.annotation.Entity;
import orm.database.connection.DatabaseConnection;
import orm.database.object.relation.Relation;
import orm.database.object.view.View;

@Entity(columnCount = 6)
public class ElectionResult extends Relation<ElectionResult> {

    @Column(name = "code_bv")
    private String codeBv;

    @Column(name = "rg_dst_cmm_fk_ctr_br")
    private String blabla;

    @Column(name = "bureau_de_vote")
    private String bureauDeVote;

    @Column(name = "id_candidat")
    private Integer candidatId;

    @Column(name = "nom")
    private String name;

    @Column(name = "parti_politique")
    private String partiPolitique;

    @Column
    private Integer voix;

    public ElectionResult() throws Exception {
        super();
    }

    public String getCodeBv() {
        return codeBv;
    }

    public void setCodeBv(String codeBv) {
        this.codeBv = codeBv;
    }

    public String getBlabla() {
        return blabla;
    }

    public void setBlabla(String blabla) {
        this.blabla = blabla;
    }

    public Integer getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Integer candidatId) {
        this.candidatId = candidatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartiPolitique() {
        return partiPolitique;
    }

    public void setPartiPolitique(String partiPolitique) {
        this.partiPolitique = partiPolitique;
    }

    public Integer getVoix() {
        return voix;
    }

    public void setVoix(Integer voix) {
        this.voix = voix;
    }

    public ElectionResult[] findAll(DatabaseConnection connection) throws Exception {
        return new View<ElectionResult>("resultat", ElectionResult.class).findAll(connection);
    }

    public ElectionResult[] findAll(DatabaseConnection connection, String spec) throws Exception {
        return new View<ElectionResult>("resultat", ElectionResult.class).findAll(connection, spec);
    }

    public String getBureauDeVote() {
        return bureauDeVote;
    }

    public void setBureauDeVote(String bureauDeVote) {
        this.bureauDeVote = bureauDeVote;
    }

}
