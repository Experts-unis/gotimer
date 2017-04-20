package model;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;


public abstract class DB {

	String url ;
	String user ;
	String passwd;
	static public String nameBase;
	static public Connection cnx;
	static public Statement dbStatement; 
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( DB.class );  
	
	public DB() {
		
		log.traceEntry("DB()") ;
		url = "jdbc:postgresql://localhost:5432/timer";
		user = "postgres";
		passwd = "superutilisateur";
		cnx = null;
		dbStatement=null;
		nameBase="timer";
		log.traceExit("DB()") ;
		
	}
	public abstract Connection getConnexion();
	public abstract Statement  getCurentDb();

}
