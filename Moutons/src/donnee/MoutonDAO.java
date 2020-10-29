package donnee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import modele.Mouton;

public class MoutonDAO {
	

	
	public int compterMoutons()
	{
		Firestore nuage = BaseDeDonnees.getInstance().getConnection();
		int nombre = 0;
		try {
			ApiFuture<QuerySnapshot> demande = nuage.collection("mouton").get();
			QuerySnapshot resultat = demande.get();
			nombre = resultat.getDocuments().size();
			System.out.println(nombre + " Moutons");
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return nombre;		
	}
	
	public List<Mouton> listerMoutons()
    {
        Firestore nuage = BaseDeDonnees.getInstance().getConnection();
        List<QueryDocumentSnapshot> moutonsNuage;
        List<Mouton> listeMoutons =  new ArrayList<Mouton>();

        System.out.println("MoutonDAO.listerMoutons()");

        try {
            ApiFuture<QuerySnapshot> demande = nuage.collection("mouton").get();
            QuerySnapshot resultat;
            resultat = demande.get();
            moutonsNuage = resultat.getDocuments();

            for(QueryDocumentSnapshot moutonNuage : moutonsNuage)
            {
            Mouton mouton = new Mouton();
            String id = moutonNuage.getId();
            String nom = moutonNuage.getString("nom");
            String couleur = moutonNuage.getString("couleur");
            double poids = moutonNuage.getDouble("poids");
            mouton.setId(id);
            mouton.setNom(nom);
            mouton.setCouleur(couleur);
            mouton.setPoids(poids);
            listeMoutons.add(mouton);
            System.out.println("id du mouton : " + id);
            //System.out.println("liste moutons:" + listeMoutons);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(listeMoutons);
        return listeMoutons;
    }

	public void ajouterMouton(Mouton mouton)
    {
        Firestore nuage = BaseDeDonnees.getInstance().getConnection();

        System.out.println("MoutonDAO.ajouterMouton()");
        try {
            DocumentReference nouveau = nuage.collection("mouton").document();
            Map<String, Object> objet = new HashMap<>();
            objet.put("nom", mouton.getNom());
            objet.put("couleur", mouton.getCouleur());
            objet.put("poids", mouton.getPoids());
            ApiFuture<WriteResult> resultat = nouveau.set(objet);
            System.out.println("Update time : " + resultat.get().getUpdateTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public Mouton detaillerMouton(int numero)
    {
        Firestore nuage = BaseDeDonnees.getInstance().getConnection();
        Mouton mouton =  new Mouton();

        System.out.println("MoutonDAO.detaillerMouton()");
            try {
                ApiFuture<QuerySnapshot> demande = nuage.collection("mouton").whereEqualTo(FieldPath.documentId(), numero).get();
                QuerySnapshot resultat = demande.get();
                List<QueryDocumentSnapshot> moutonsNuage = resultat.getDocuments();
                QueryDocumentSnapshot moutonNuage = moutonsNuage.get(0);
                String id = moutonNuage.getId();
                String nom = moutonNuage.getString("nom");
                String couleur = moutonNuage.getString("couleur");
                double poids = moutonNuage.getDouble("poids");
                mouton.setId(id);
                mouton.setNom(nom);
                mouton.setCouleur(couleur);
                mouton.setPoids(poids);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return mouton;
    }
	
}

