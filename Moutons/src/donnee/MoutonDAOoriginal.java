package donnee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Mouton;

public class MoutonDAO {
	
	public static final String SQL_LISTER_MOUTONS = "SELECT * FROM mouton";
	public static final String SQL_DETAILLER_MOUTON = "SELECT * from mouton WHERE id = ?";
	public static final String SQL_AJOUTER_MOUTON = "INSERT into mouton(nom, couleur, poids) VALUES(?,?,?)";
	public static final String SQL_MODIFIER_MOUTON = "";
	public static final String SQL_COMPTER_MOUTON = "SELECT COUNT(*) as nombre from mouton";
	
	public int compterMoutons()
	{
		Connection connection = BaseDeDonnees.getInstance().getConnection();
		
		Statement requete;
		try {
			requete = connection.createStatement();
			ResultSet curseur = requete.executeQuery(SQL_COMPTER_MOUTON);
			if(curseur.next())
			{
				int nombre = curseur.getInt("nombre");
				return nombre;
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		return 0;		
	}
	
	public List<Mouton> listerMoutons()
	{
		Connection connection = BaseDeDonnees.getInstance().getConnection();
		
		List<Mouton> moutons =  new ArrayList<Mouton>();			
		Statement requete;
		try {
			requete = connection.createStatement();
			ResultSet curseur = requete.executeQuery(SQL_LISTER_MOUTONS);
			while(curseur.next())
			{
				int id = curseur.getInt("id");
				String nom = curseur.getString("nom");
				String couleur = curseur.getString("couleur");
				double poids = curseur.getDouble("poids");
				Mouton mouton = new Mouton();
				mouton.setId(id);
				mouton.setNom(nom);
				mouton.setCouleur(couleur);
				mouton.setPoids(poids);
				moutons.add(mouton);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		return moutons;
	}

	public void ajouterMouton(Mouton mouton)
	{
		Connection connection = BaseDeDonnees.getInstance().getConnection();
		
		System.out.println("MoutonDAO.ajouterMouton()");
		try {
			PreparedStatement requete = connection.prepareStatement(SQL_AJOUTER_MOUTON);
			requete.setString(1, mouton.getNom());
			requete.setString(2, mouton.getCouleur());
			requete.setDouble(3, mouton.getPoids());
			
			requete.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Mouton detaillerMouton(int numero)
	{
		Connection connection = BaseDeDonnees.getInstance().getConnection();
		
		Mouton mouton =  new Mouton();			
		PreparedStatement requete;
			try {
				requete = connection.prepareStatement(SQL_DETAILLER_MOUTON);
				requete.setInt(1, numero);
				
				ResultSet curseurCollection = requete.executeQuery();
				curseurCollection.next();
				int id = curseurCollection.getInt("id");
				String nom = curseurCollection.getString("nom");
				String couleur = curseurCollection.getString("couleur");
				double poids = curseurCollection.getDouble("poids");
				mouton.setId(id);
				mouton.setNom(nom);
				mouton.setCouleur(couleur);
				mouton.setPoids(poids);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return mouton;
	}
	
}package donnee;

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

