/**
 * 
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;


/**
 * @author test
 *
 */
public abstract class TableDB {

	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TableDB.class ); 
	protected String nameTable;
	protected String nameID;
	protected String queryList;
	static protected DB  driver;
	protected String queryInsert;
	protected String queryUpdate;
	protected String queryDelete;
	private int id;
	public PreparedStatement dbPrepInsert;
	public PreparedStatement dbPrepUpdate;
	public PreparedStatement dbPrepDelete;
	
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public TableDB(String nameTable,String nameID) throws ExceptionTraitementSQL {
		
		log.traceEntry("DBTable(String nameTable="+nameTable+",String nameID="+nameID+")");
		
		this.nameTable=nameTable;
		this.nameID = nameID;
		//this.driver = driver;
		//Recherche de l'identifiant unique
		
		setQueryList("SELECT * FROM "+nameTable);
		
		id = initID();

		dbPrepInsert = null;
		queryInsert = "";
		prepareInsert();
		dbPrepDelete = null;
		queryDelete = "";
		prepareDelete();
		
		dbPrepUpdate = null;
		queryUpdate = "";
		prepareUpdate();

		log.traceExit("DBTable()");
	}
	/** Fonction générique de préparation d'une requette
	 * @param query : la requette SQL
	 * @return un objet de type PreparedStatement
	 * @throws ExceptionTraitementSQL si la requette ne peut pas être compilée
	 */
	protected PreparedStatement  prepareQuery(String query) throws ExceptionTraitementSQL
	{
		log.traceEntry("prepareQuery(String query="+query+")");
		PreparedStatement dbPrepQuery;
		try {
			dbPrepQuery = DB.cnx.prepareStatement(query);
			log.trace("prepareQuery OK");
			return dbPrepQuery;

		} catch (SQLException e) {
			log.fatal("prepareQuery  KO " );
			throw new ExceptionTraitementSQL(e,query);
		}
		
	}
	
	
	
	
	/** Fonction générique de préparation d'un delete sur une table SQL
	 * @throws ExceptionTraitementSQL
	 */
	public void prepareDelete() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareDelete()");
		//On crée l'objet avec la requête en paramètre
		
		queryDelete ="DELETE  FROM "+nameTable+" WHERE "+nameID+"=?";
	    this.dbPrepDelete = prepareQuery(queryDelete);
	
	    log.traceExit("prepareDelete()") ;
	    
	}
	
	/** Fonction d'appel du delete sur une table à partir d'une requette préparée
	 * @param psDelete l'objet PreparedStatement de la requette delete préparée
	 * @param id la valeur de l'identifiant des lignes à supprimer
	 * @throws ExceptionTraitementSQL
	 */
	public void delete(PreparedStatement psDelete, int id) throws ExceptionTraitementSQL {

		log.traceEntry("delete(int id="+id+")");
		try 
		{
			//On paramètre notre requête préparée
			psDelete.setInt(1, id);

			//On exécute
			psDelete.executeUpdate();	

		} catch (SQLException e) {
			log.fatal("delete : Rq KO "+psDelete.toString() );
			log.fatal("printStackTrace ", e);
			throw new ExceptionTraitementSQL(e,psDelete.toString());

		}

		log.traceExit("delete() OK") ;

		
	}
	/** Fonction d'appel du delete sur la table 
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
	public void delete(int id) throws ExceptionTraitementSQL {
		
		delete(dbPrepDelete, id);
		
	}

	public abstract void prepareInsert() throws ExceptionTraitementSQL ;
	public abstract void prepareUpdate() throws ExceptionTraitementSQL ;


	/** Fonction générique de chargement d'élément dans un Vector
	 * @throws ExceptionTraitementSQL
	 */
	protected void loadListGenerique() throws ExceptionTraitementSQL {

		log.traceEntry("loadListGenerique() ");
		
		
		initListObject();
		
		ResultSet result =null;
		
		String query =getQueryList();
		
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			
			result = DB.dbStatement.executeQuery(query);
	
			log.trace("getList : Rq OK " +query);
			
			int size=0;
			while(result.next()){ 
				
				addNewElementInAVectorList(result);
				++size;
			}

			log.traceExit("loadListGenerique  OK  size="+size) ;
			
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+queryList);
			log.fatal("printStackTrace " , e);

			log.traceExit("loadListGenerique() KO ") ;
			throw new ExceptionTraitementSQL(e,queryList);
			
			
		}
		
		
	}
	
	/** Fonction abstraite utiliser pour le chargement effectif dans la liste
	 * @param result
	 * @throws SQLException 
	 */
	protected abstract void addNewElementInAVectorList(ResultSet result) throws SQLException ;

	/** Fonction d'initialisation de la requete pour la fonction loadListGenerique
	 * 
	 */
	protected abstract void initListObject()  ;
	
	/**
	 * @return the queryList
	 */
	public String getQueryList() {
		return queryList;
	}
	/**
	 * @param queryList the queryList to set
	 */
	public void setQueryList(String queryList) {
		this.queryList = queryList;
	}
	
	/** Initialise la valeur de l'ID de la table
	 * @return
	 * @throws ExceptionTraitementSQL
	 */
	public int initID() throws ExceptionTraitementSQL
	{
	
		log.traceEntry("initID()");
		//Recherche de l'identifiant unique suivant pour cette table
		int unId=-1;
		String query ="SELECT MAX("+nameID+") FROM "+nameTable;
	
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			ResultSet result = DB.dbStatement.executeQuery(query);
			result.first();
			unId = result.getInt(1);
			
			log.trace("initID : Rq OK "+query +" res = "+unId );

		} catch (SQLException e) {
			log.trace("initID : Rq KO "+query );
			
			throw new ExceptionTraitementSQL(e,query);
		}
		
		return unId;
	}
	
	/** 
	 * @return la valeur max de l'identifiant  de la table
	 */
	public int getCurrentID(){
		log.traceEntry("getCurrentID() <--> "+id);
		return id;
		
	}
	/**
	 * @return la valeur max+1 de l'identifiant de la table
	 */
	public int getNextID(){
		log.traceEntry("getNextID() <--> 1+"+id);
		return ++id;
		
	}
	
public void updateBoolean(PreparedStatement psUpdate, int id, boolean value) throws ExceptionTraitementSQL {
		
		log.traceEntry("updateBoolean(psUpdate="+psUpdate.toString()+", int id="+id+", boolean value="+value+")");
		//Modifier une référence dans la table  
		
		try 
		{
				
			//On paramètre notre requête préparée
			psUpdate.setBoolean(1, value);
			psUpdate.setInt(2, id);

			//On exécute
			psUpdate.executeUpdate();	
			
			log.traceExit("updateBoolean() OK") ;
			
		} catch (SQLException e) {
			log.fatal("Rq KO "+psUpdate.toString() );
			log.fatal("printStackTrace " , e);
			log.traceExit("updateBoolean() KO") ;
			throw new ExceptionTraitementSQL(e,psUpdate.toString());
			
		}
		
	}

}
