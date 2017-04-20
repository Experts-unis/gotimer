package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;
//import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionInitBase;
import controleur.Principale;

public class DBProgres extends DB  {
	
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( DBProgres.class );  

	public DBProgres() throws  ExceptionInitBase{
		super();
		
		log.traceEntry("DBProgres(ResourceBundle config)") ;
		
		// Charge le fichier du paramétrage de l'application
		nameBase = Principale.config.getProperty("sgbd.name").trim();
		//nameBase="timerprod";
		String user = "postgres";
		String passwd = "superutilisateur";
		
		try {
			Class.forName("org.postgresql.Driver");
	
			//String url = "jdbc:postgresql://localhost:5432/timer";
//			String url = config.getString("sgbd.name"); //"domaine.properties.sgbd.name";
//			String user = config.getString("sgbd.user"); //"domaine.properties.sgbd.user";
//			String passwd = config.getString("sgbd.mdp"); //"domaine.properties.sgbd.mdp";
			
			//nameBase = "timerprod";
			String url = "jdbc:postgresql://localhost:5432/"+nameBase;
	
			cnx = DriverManager.getConnection(url, user, passwd);
			
			dbStatement = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			log.trace("Driver O.K.");
			
		} catch (Exception e) {
			
			log.fatal("Erreur connexion database DBProgress ! url="+url+" user="+user+" mdp="+passwd);
			log.fatal("printStackTrace " , e);
			
			log.traceExit("DBProgres() KO") ;
			
			throw new ExceptionInitBase(e,url,user);
		}  
		log.traceExit("DBProgres()") ;
	}

	@Override
	public Connection getConnexion() {
		
		return cnx;
	}

	@Override
	public Statement getCurentDb() {
		
		return dbStatement;
	}

}
