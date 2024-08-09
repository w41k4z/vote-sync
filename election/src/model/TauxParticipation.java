package model;

import orm.annotation.Column;
import orm.database.connection.DatabaseConnection;
import orm.database.object.relation.Relation;
import orm.database.object.view.View;

public class TauxParticipation extends Relation<TauxParticipation> {

    @Column(name = "code_bv")
    private String codeBv;

    @Column(name = "bureau_de_vote")
    private String bureauDeVote;

    @Column(name = "taux_de_participation")
    private Double taux;

    public TauxParticipation() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @return String return the codeBv
     */
    public String getCodeBv() {
        return codeBv;
    }

    /**
     * @param codeBv the codeBv to set
     */
    public void setCodeBv(String codeBv) {
        this.codeBv = codeBv;
    }

    /**
     * @return String return the bureauDeVote
     */
    public String getBureauDeVote() {
        return bureauDeVote;
    }

    /**
     * @param bureauDeVote the bureauDeVote to set
     */
    public void setBureauDeVote(String bureauDeVote) {
        this.bureauDeVote = bureauDeVote;
    }

    /**
     * @return Double return the taux
     */
    public Double getTaux() {
        return taux;
    }

    /**
     * @param taux the taux to set
     */
    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public TauxParticipation[] findAll(DatabaseConnection connection) throws Exception {
        return new View<TauxParticipation>("resultat_avec_taux_participation", TauxParticipation.class)
                .findAll(connection);
    }

    public TauxParticipation[] findAll(DatabaseConnection connection, String spec) throws Exception {
        return new View<TauxParticipation>("resultat_avec_taux_participation", TauxParticipation.class)
                .findAll(connection, spec);
    }
}
