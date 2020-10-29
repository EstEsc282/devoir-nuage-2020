package donnee;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

public class BaseDeDonnees {
    private Firestore nuage = null;
    String ID_Projet = "infonuagique-2b75b";

    private BaseDeDonnees()
    {
        try {
            Credentials credit = GoogleCredentials.fromStream(new FileInputStream("cle-bergerie.json"));
            nuage = FirestoreOptions.getDefaultInstance().toBuilder().setCredentials(credit).setProjectId(ID_Projet).build().getService();
            System.out.println("Base de donnees");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // SINGLETON - DEBUT
    private static BaseDeDonnees instance = null;
    public static BaseDeDonnees getInstance()
    {
        if(null == instance) instance = new BaseDeDonnees();
        return instance;
    }
    // SINGLETON - FIN

    public Firestore getConnection()
    {
        return this.nuage;
    }

}