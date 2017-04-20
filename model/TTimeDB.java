/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
/**
 * @author test
 *
 */
public class TTimeDB extends TableDB {

	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TTimeDB.class ); 
	
	private String queryDeleteProjet;
	private PreparedStatement dbPrepDeleteProjet;
	private String queryDeleteFonction;
	private PreparedStatement dbPrepDeleteFonction;
	private String queryArchiveFonction;
	private PreparedStatement dbPrepArchiveFonction;
	private String queryArchiveTempsDuProjet;
	private PreparedStatement dbPrepArchiveTempsDuProjet;
	
//	private String queryReportFonction;
//	private PreparedStatement dbPrepReportFonction;
	

	/**
	 * @param nameTable
	 * @param nameID
	 * @param driver
	 * @throws ExceptionTraitementSQL 
	 */
	public TTimeDB() throws ExceptionTraitementSQL {
		super("TTIME", "IDTIME");
		
		log.traceEntry("TTimeModel()") ;
		prepareDeleteProjet();
		prepareDeleteFonction() ;
		prepareArchiveFonction() ;
		prepareArchiveTempsDuProjet();
		
		//prepareReportFonction();
		
		log.traceExit("TTimeModel()") ;
		
	}

	/* (non-Javadoc)
	 * @see model.TableModel#prepareInsert()
	 */
	@Override
	public void prepareInsert() throws ExceptionTraitementSQL {
		
		//On crée l'objet avec la requête en paramètre
		// System.out.println("Appel de prepareInsert dans TProjetModel");
		queryInsert ="INSERT INTO TTIME (idtime, dt, idproj, idfonc, charge, dansplage,horsplage,pro,start, stop, archive) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?, false)";
		this.dbPrepInsert = prepareQuery(queryInsert);
		
	}
	
	/* (non-Javadoc)
	 * @see model.TableModel#prepareDelete()
	 */
	@Override
	public void prepareDelete() throws ExceptionTraitementSQL {
	
		//On crée l'objet avec la requête en paramètre
		// System.out.println("Appel de prepareDelete");
		queryDelete ="DELETE  FROM TTIME WHERE IDTIME=?";
		this.dbPrepDelete = prepareQuery(queryDelete);

	    
	}
	public void prepareDeleteProjet() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareDeleteProjet()") ;
		//On crée l'objet avec la requête en paramètre
		// System.out.println("Appel de prepareDeleteProjet");
		queryDeleteProjet ="DELETE  FROM TTIME WHERE IDPROJ=?";
		this.dbPrepDeleteProjet = prepareQuery(queryDeleteProjet);
	    
	}

	public void prepareDeleteFonction() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareDeleteFonction()") ;
		//On crée l'objet avec la requête en paramètre
		// System.out.println("Appel de prepareDeleteFonction");
		queryDeleteFonction ="DELETE FROM TTIME WHERE IDFONC=?";
		this.dbPrepDeleteFonction = prepareQuery(queryDeleteFonction);

	    
	}
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	private void prepareArchiveFonction() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareArchiveFonction()") ;
		//On crée l'objet avec la requête en paramètre
		
		queryArchiveFonction ="UPDATE TTIME SET ARCHIVE=? WHERE IDFONC=?";
		this.dbPrepArchiveFonction = prepareQuery(queryArchiveFonction);
	
	}
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	private void prepareArchiveTempsDuProjet() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareArchiveTempsDuProjet()") ;
		//On crée l'objet avec la requête en paramètre
		
		queryArchiveTempsDuProjet ="UPDATE TTIME SET ARCHIVE=? WHERE IDPROJ=?";
		this.dbPrepArchiveTempsDuProjet = prepareQuery(queryArchiveTempsDuProjet);
	
	}
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
//	private void prepareReportFonction() throws ExceptionTraitementSQL {
//		
//		log.traceEntry("prepareReportFonction()") ;
//		//On crée l'objet avec la requête en paramètre
//		
//		queryReportFonction ="UPDATE TTIME SET REPORT=? WHERE IDFONC=?";
//		this.dbPrepReportFonction = prepareQuery(queryReportFonction);
//	
//	}
	/* (non-Javadoc)
	 * @see model.TableModel#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() throws ExceptionTraitementSQL {
		
		// Prépare la modification de la table ttime
		queryUpdate="UPDATE TTIME SET idproj=?, idfonc=?,charge =?, dansplage=?, horsplage=?, pro=?,start=?, stop=? WHERE IDTIME=?";
		this.dbPrepUpdate =prepareQuery(queryUpdate);
	}
	
	
	/**
	 * @param archive 
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector <DataInfoSuiviProjet> getDataSuiviProjet(int idproj, boolean archive) throws ExceptionTraitementSQL {

		log.traceEntry("Vector <DataInfoSuiviProjet> getDataSuiviProjet()") ;

		ResultSet result =null;
 		
		String query ="SELECT PROJET_NAME, FONCTION_NAME, IDCOMPLEXITE , DEBUT, FIN,DELAI, c.ARCHIVE,  CHARGE, INITIALE, OLDRESTEAFAIRE ,RESTEAFAIRE , id as idraf"
	+ " FROM vsuiviconsodetails c, vestimationsdetails e "
    + " WHERE c.idprojet=e.idprojet and c.idfonc=e.idfonc and "  
		+ "  c.REPORT=false AND e.REPORT=false and c.IDPROJET="+idproj ;
		query +=" AND  c.ARCHIVE =" + ((archive )? "true" : "false");
		query +=" ORDER BY DEBUT";
		
		log.trace("query="+query);
		
		Vector <DataInfoSuiviProjet> list = new Vector <DataInfoSuiviProjet>();
		

		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);
			
			
			while(result.next()){ 
				// DataInfoSuiviProjet( String projetName, String foncName,int idComplexite,  java.sql.Date debut,double  charge, 
				//double estimation, double  resteafaire)
 
				list.addElement(
						new DataInfoSuiviProjet(
						result.getString("PROJET_NAME"),
						result.getString("FONCTION_NAME"),
						result.getInt("IDCOMPLEXITE"),
						result.getDate("DEBUT"),
						result.getDate("FIN"),
						result.getInt("DELAI"),
						result.getBoolean("ARCHIVE"),
						result.getDouble("CHARGE"),
						result.getDouble("INITIALE"),
						result.getDouble("OLDRESTEAFAIRE"),
						result.getDouble("RESTEAFAIRE")));
				
			}
			

			
			log.traceExit("Vector <DataInfoLigneTemps> getDataSuiviProjet() OK -> "+list.size()) ;
			return list;

		} catch (SQLException e) {


			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);
			log.traceExit("Vector <DataInfoLigneTemps> getDataSuiviProjet() KO") ;
			throw new ExceptionTraitementSQL(e,query);


		}
	}
	
	public Vector <DataInfoConso> getDataConso(int idProjet) throws ExceptionTraitementSQL {

		log.traceEntry("Vector <DataInfoConso> getDataConso(int idProjet="+idProjet+")") ;

		ResultSet result =null;
		String query ="SELECT  IDPROJET,IDFONC, FONCTION_NAME, DEBUT, FIN, DELAI , CHARGE , NBRCHRONOS "
				+ " FROM vsuiviconsodetails "
				+ " WHERE  IDPROJET = "+idProjet 
				+ " ORDER BY IDFONC";
		
		log.trace("query="+query);
		
		Vector <DataInfoConso> list = new Vector <DataInfoConso>();
		
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);

			 
			while(result.next()){ 
				list.addElement(new DataInfoConso(result.getInt("IDPROJET"),result.getInt("IDFONC"),
						result.getString("FONCTION_NAME"),result.getDate("DEBUT"),result.getDate("FIN"),
						result.getDouble("DELAI"),result.getDouble("CHARGE"),result.getInt("NBRCHRONOS")));				
			}
			
			log.traceExit("Vector <DataInfoConso> getDataConso() OK -> "+list.size()) ;
			return list;

		} catch (SQLException e) {


			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);

			log.traceExit("Vector <DataInfoConso> getDataConso(int idProjet) KO") ;
			throw new ExceptionTraitementSQL(e,query);

		}
	}
	
	public Vector <DataInfoTimer> getDataTimer(int idProjet) throws ExceptionTraitementSQL {

		log.traceEntry("Vector <DataInfoTime> getDataTimer(int idProjet ="+idProjet+")") ;
		ResultSet result =null;
		String query ="SELECT t.IDTIME, t.DT,t.IDPROJ,p.LIBELLE as PROJET_NAME ,t.IDFONC, f.LIBELLE as FONCTION_NAME, START,STOP, CHARGE, PRO, DANSPLAGE,HORSPLAGE  ";
		query += "FROM  TTIME as t, TPROJETS as p, TFONCTIONS as f ";
		query += "WHERE t.ARCHIVE=false  AND t.IDPROJ=p.IDPROJET and t.IDPROJ=f.IDPROJET and t.IDFONC=f.IDFONC ";
		if (idProjet != -1 )
			query += " AND t.iDPROJ="+idProjet+" ";
		query += "ORDER BY DT, START";
		
		Vector <DataInfoTimer> list = new Vector <DataInfoTimer>();
		
		
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);
			
			
			while(result.next()){ 

//				public DataInfoTimer(int id, java.sql.Date dt, int idproj, String libProjet,
//						int idfonc, String libFonction, boolean pro, double horsplage,
//						double dansplage, double charge, Timestamp start, Timestamp stop)
				
				list.addElement(new DataInfoTimer(
						result.getInt("IDTIME"),
						result.getDate("DT"),
						result.getInt("IDPROJ"),result.getString("PROJET_NAME"),
						result.getInt("IDFONC"),result.getString("FONCTION_NAME"),
						result.getBoolean("PRO"),
						result.getDouble("HORSPLAGE"),
						result.getDouble("DANSPLAGE"),
						result.getDouble("CHARGE"),
						result.getTimestamp("START"),
						result.getTimestamp("STOP")
						));
				
			}
			
			log.trace("getDataTimer() query : "+query);
			
			log.traceExit("Vector <DataInfoTime> getDataTimer() OK -> " +list.size()) ;
			return list;

		} catch (SQLException e) {
			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);
			log.traceExit("Vector <DataInfoTime> getDataTimer() KO") ;
			throw new ExceptionTraitementSQL(e,query);
		}

	}
	





	public int add(Timestamp date,int idProj,int idFonc,double charge,double horsplg,double dansplg,boolean pro,Timestamp start,Timestamp stop) throws ExceptionTraitementSQL {
		
		//Ajouter une référence de dépense temps dans la table TTIME
		
		log.traceEntry("add(Timestamp date="+date+",int idProj="+idProj
				+",int idFonc="+idFonc+",Double charge="+charge+",Double horsplg="+horsplg+",Double dansplg="+dansplg+",Boolean pro="+pro
				+",Timestamp start="+start+",Timestamp stop="+stop+")") ;

		
		
		try 
		{
			// System.out.println("Rq sur tfonctions OK "+queryInsert );
			//On paramètre notre requête préparée
			// INSERT INTO TTIME (idtime, dt, idproj, idfonc, charge, dansplage,horsplage,pro,start, stop, archive) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?, false)
			int n=1;
			dbPrepInsert.setInt(n++, this.getNextID());
			dbPrepInsert.setDate(n++, new Date(date.getTime()));
			
			dbPrepInsert.setInt(n++, idProj);
			dbPrepInsert.setInt(n++, idFonc);
			
			dbPrepInsert.setDouble(n++, charge);
			dbPrepInsert.setDouble(n++, dansplg);
			dbPrepInsert.setDouble(n++, horsplg);
			dbPrepInsert.setBoolean(n++, pro);
			dbPrepInsert.setTimestamp(n++, start);
			dbPrepInsert.setTimestamp(n++, stop);
   			//On exécute
			dbPrepInsert.executeUpdate();	
			
			int ret = getCurrentID();
			
			log.traceExit("add(...) OK ->"+ret) ;
			return ret;
		} catch (SQLException e) {
			// System.out.println("Rq sur ttime KO "+dbPrepInsert.toString()  );
			log.fatal("Rq KO "+dbPrepInsert.toString() );
			log.fatal("printStackTrace " , e);
			log.traceExit("add(...) KO -> -1") ;
			throw new ExceptionTraitementSQL(e,dbPrepInsert.toString() );
		}
		
	}

	/**
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
	public void deleteProjet(int id) throws ExceptionTraitementSQL {
		
		log.traceEntry("deleteProjet(int id="+id+")") ;
		
		
		
		super.delete(dbPrepDeleteProjet, id);
		
		log.traceExit("deleteProjet(int id)") ;
	
		
	}
	/**
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
	public void deleteFonction(int id) throws ExceptionTraitementSQL {

		log.traceEntry("deleteFonction(int id="+id+")") ;
		
		super.delete(dbPrepDeleteFonction, id);
		
		log.traceExit("deleteFonction(int id)") ;
		
	}

	
	/**
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
	public void archiveFonction(int id,boolean value) throws ExceptionTraitementSQL {
		log.traceEntry("archiveFonction(int id="+id+",boolean value="+value+")") ;
		updateBoolean(dbPrepArchiveFonction, id, value);
		log.traceExit("archiveFonction( )") ;
		
	}
	/**
	 * @param id
	 * @param value
	 * @throws ExceptionTraitementSQL 
	 */
	public void archiveTempsDuProjet(int id, boolean value) throws ExceptionTraitementSQL {
		
		log.traceEntry("archiveTempsDuProjet(int id="+id+",boolean value="+value+")") ;
		updateBoolean(dbPrepArchiveTempsDuProjet, id, value);
		log.traceExit("archiveTempsDuProjet( )") ;
	}
	
	/**
	 * @param id
	 * @param value
	 * @throws ExceptionTraitementSQL 
	 */
//	public void reportFonction(int id , boolean value) throws ExceptionTraitementSQL {
//		log.traceEntry("reportFonction(int id="+id+",boolean value="+value+")") ;
//		updateBoolean(dbPrepReportFonction, id, value);
//		log.traceExit("reportFonction(int id)") ;
//	}
	

	/**
	 * @param id
	 * @param sqlDt
	 * @param idproj
	 * @param idfonc
	 * @param delai
	 * @param charge
	 * @param sqlStart
	 * @param sqlStop
	 * @throws ExceptionTraitementSQL 
	 */
	public void update(int id, 
			Timestamp dt, 
			int idproj, int idfonc,  double charge, double horsplg,double dansplg,boolean pro,
			Timestamp start,Timestamp stop) throws ExceptionTraitementSQL {

		log.traceEntry("update(int id="+id+", " 
			+" Timestamp dt = "+dt.toString()+", "
			+" int idproj="+idproj+", int idfonc="+idfonc
			+", Double charge="+charge+", Double horsplg="+horsplg+", Double dansplg="+dansplg
			+", Boolean pro="+pro
			+", Timestamp start="+start+",Timestamp stop="+stop+")") ;


		//UPDATE TTIME SET idproj=?, idfonc=?,charge =?, dansplage=?, horsplage=?, pro=?,start=?, stop=? WHERE IDTIME=?
		try 
		{
			//On paramètre notre requête préparée
		
			int n=1;
			
			dbPrepUpdate.setInt(n, idproj);
			dbPrepUpdate.setInt(++n, idfonc);
			dbPrepUpdate.setDouble(++n, charge);
			dbPrepUpdate.setDouble(++n, dansplg);	
			dbPrepUpdate.setDouble(++n, horsplg);
			dbPrepUpdate.setBoolean(++n, pro);			
			dbPrepUpdate.setTimestamp(++n, start);
			dbPrepUpdate.setTimestamp(++n, stop);
			dbPrepUpdate.setInt(++n, id);
			
   			//On exécute
			dbPrepUpdate.executeUpdate();	
				
			log.traceExit("update(...) OK ->") ;
			
		} catch (SQLException e) {
			// System.out.println("Rq sur ttime KO "+dbPrepInsert.toString()  );
			log.fatal("Rq KO "+dbPrepUpdate.toString() );
			log.fatal("printStackTrace " , e);
			log.traceExit("update(...) KO ") ;
			throw new ExceptionTraitementSQL(e,dbPrepUpdate.toString() );
			
		}
		
	}

	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) {
		// Rien a faire pour le moment
		
	}

	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		// pas utilisé
		
	}





	

}
