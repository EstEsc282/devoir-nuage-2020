package controleur;

import donnee.MoutonDAO;
import vue.Vue;
import vue.VueTroupeau;

public class Controleur {

    public static Vue selectionnerVuePrincipale() 
    {
        MoutonDAO dao = new MoutonDAO();
        VueTroupeau.getInstance().afficherNombreMoutons(dao.compterMoutons());
        VueTroupeau.getInstance().afficherListeMoutons(dao.listerMoutons());
        return VueTroupeau.getInstance();
    }


}