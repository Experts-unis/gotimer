/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
public class TVacationsDB extends TableDB {

	
	Vector <DataInfoVacation> list;
	/**
	 * @param nameTable
	 * @param nameID
	 * @throws ExceptionTraitementSQL 
	 */
	public TVacationsDB() throws ExceptionTraitementSQL {
		super("TVACATIONS", "ID");
		
	}

	public Vector <DataInfoVacation> getList() throws ExceptionTraitementSQL{
		
		super.loadListGenerique();
		
		return list;
	}
	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		
	
		list = new Vector <DataInfoVacation>();
	}

	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) throws SQLException {
		
		DataInfoVacation element=null;


		element = new DataInfoVacation(result.getString("DEBUT"),result.getString("FIN"),result.getInt("ID"));
		list.addElement(element);


		
		
		
	}

	/* (non-Javadoc)
	 * @see model.TableModel#prepareInsert()
	 */
	@Override
	public void prepareInsert() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareInsert()");
		
		//On crée l'objet avec la requête en paramètre
		// System.out.println("Appel de prepareInsert dans TFonctionModel");
		queryInsert ="INSERT INTO TVACATIONS (ID,DEBUT,FIN) values (?,?,?)";
		this.dbPrepInsert = prepareQuery(queryInsert);
		log.traceExit("prepareInsert()") ;

	}

	public  int add(Timestamp debut,Timestamp  fin) throws ExceptionTraitementSQL {
		
		log.traceEntry("add(String debut="+debut+",String fin="+fin+")");
		
		//Ajouter une référence de projet dans la table TPERIODES
		try 
		{
			
			//On paramètre notre requête préparée
			dbPrepInsert.setInt(1, this.getNextID());
			dbPrepInsert.setTimestamp(2, debut);
			dbPrepInsert.setTimestamp(3, fin);
	
   			//On exécute
			dbPrepInsert.executeUpdate();	
			
			int ret = getCurrentID();
		
			log.traceExit("add(...) OK => id="+ret) ;
			return ret;
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+dbPrepInsert.toString());
			log.fatal("printStackTrace " , e);
			
			log.traceExit("add(..) KO => -1") ;
			
			throw new ExceptionTraitementSQL(e,dbPrepInsert.toString());
			
		}
	}

	/* (non-Javadoc)
	 * @see model.DBTable#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() {
		// pas utilisé
		
	}


}
