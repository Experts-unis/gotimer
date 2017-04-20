/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Vector;

import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
public class TPeriodeDB extends TableDB {

	Vector <DataInfoPeriodeActivite> list;
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public TPeriodeDB() throws ExceptionTraitementSQL {
		super("TPERIODES","ID");
		
	}

	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		
		list = new Vector <DataInfoPeriodeActivite>();
	}

	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) throws SQLException {
		
		DataInfoPeriodeActivite element=null;
		element = new DataInfoPeriodeActivite(result.getString("DEBUT"),result.getString("FIN"),result.getInt("ID"));
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
		queryInsert ="INSERT INTO TPERIODES (ID,DEBUT,FIN,DUREE) values (?,?,?,?)";
		this.dbPrepInsert = prepareQuery(queryInsert);
		log.traceExit("prepareInsert()") ;

	}

	public int add(String debut,String fin, double duree) throws ExceptionTraitementSQL {
		log.traceEntry("add(String debut="+debut+",String fin="+fin+", duree="+duree+")");
		
		//Ajouter une référence de projet dans la table TPERIODES
		try 
		{
			
			//On paramètre notre requête préparée
			dbPrepInsert.setInt(1, this.getNextID());
			dbPrepInsert.setString(2, debut);
			dbPrepInsert.setString(3, fin);
			dbPrepInsert.setDouble(4, duree);
			
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

	/**
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector<DataInfoPeriodeActivite> getList() throws ExceptionTraitementSQL {
		super.loadListGenerique();
		
		return list;
	}
	

	/* (non-Javadoc)
	 * @see model.TableDB#getQueryList()
	 */
	@Override
	public String getQueryList() {
		// TODO Auto-generated method stub
		return "SELECT * FROM TPERIODES ORDER BY DEBUT";
	}

	/* (non-Javadoc)
	 * @see model.DBTable#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() {
		// pas utilise
		
	}







}
