package vue;
import java.util.List;
import java.util.Set;

import com.sun.media.jfxmedia.logging.Logger;

import controleur.ControleurTroupeau;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import modele.Mouton;

public class VueTroupeau extends Vue {

	protected ControleurTroupeau controleur;
	protected static VueTroupeau instance = null; 
	public static VueTroupeau getInstance() {if(null==instance)instance = new VueTroupeau();return VueTroupeau.instance;}; 
	
	private VueTroupeau() 
	{
		super("troupeau.fxml"); 
		super.controleur = this.controleur = new ControleurTroupeau();
		Logger.logMsg(Logger.INFO, "new VueTroupeau()");
	}
		
	public void activerControles()
	{
		super.activerControles();
		
	}
	
	public void afficherNombreMoutons(int nombre)
	{
		Label vueNombre = (Label)lookup("#nombre-mouton");
		vueNombre.setText(nombre + " têtes");
	}

	public void afficherListeMoutons(List<Mouton> moutons) 
	{
		AnchorPane vueCadre = (AnchorPane)lookup("#liste-mouton");
		ScrollPane vueEtirable = new ScrollPane();
		vueCadre.getChildren().clear();
		vueCadre.getChildren().add(vueEtirable);
		FlowPane vueListe = new FlowPane();
		vueEtirable.setContent(vueListe);
		vueListe.setPrefWidth(1085);
		for(Mouton mouton:moutons)
		{
			Pane vueMouton = new Pane();
			vueMouton.setPrefWidth(245);
			vueMouton.setPrefHeight(160);
			vueMouton.getStyleClass().add("mouton");
			FlowPane.setMargin(vueMouton, new Insets(10, 10, 10, 10));
			Label vueNom = new Label(mouton.getNom());
			vueNom.setStyle("-fx-font-size:50px;-fx-padding:10;");
			Label vueCouleur = new Label(mouton.getCouleur());
			vueCouleur.setStyle("-fx-font-size:25px;-fx-padding:10;-fx-text-fill:red");
			vueCouleur.setLayoutY(55);
			Label vuePoids = new Label(mouton.getPoids() + " kg");
			vuePoids.setStyle("-fx-font-size:25px;-fx-padding:10;-fx-text-fill:red");
			vuePoids.setLayoutY(85);
			vueMouton.getChildren().add(vueNom);
			vueMouton.getChildren().add(vueCouleur);
			vueMouton.getChildren().add(vuePoids);
			vueListe.getChildren().add(vueMouton);
		}
		/*    <FlowPane.margin>
       <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
    </FlowPane.margin>
*/
/*		Set<Node> listeVuesMouton = vueListe.lookupAll("Pane");
		for(Node noeud : listeVuesMouton)
		{
			Pane vueMouton = (Pane)noeud;
			vueMouton.getChildren().add(new Label("TEST TEST TEST"));
		}*/
		
	}
}
