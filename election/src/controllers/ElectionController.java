package controllers;

import connection.AppConnection;
import etu2011.framework.annotations.ModelController;
import etu2011.framework.annotations.Scope;
import etu2011.framework.annotations.UrlMapping;
import etu2011.framework.enumerations.Scopes;
import etu2011.framework.renderer.ModelView;
import model.ElectionResult;

@ModelController(route = "/election")
@Scope(Scopes.PROTOTYPE)
public class ElectionController {

    @UrlMapping(url = "/resultat")
    public ModelView getResultat() throws Exception {
        ModelView modelView = new ModelView("result.jsp");
        modelView.addData("results", new ElectionResult().findAll(new AppConnection()));
        return modelView;
    }
}
