
import donnee.MoutonDAO;
import modele.Mouton;
import vue.Fenetre;

public class App {

	public static void main(String[] parametres) {
		
		MoutonDAO dao = new MoutonDAO();
		Mouton mouton = new Mouton();
		mouton.setNom("Coucou");
		dao.ajouterMouton(mouton);
		
		Fenetre.launch(Fenetre.class, parametres);	
	}

}
