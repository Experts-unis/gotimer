/**
 * 
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;



import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;



/**
 * @author test
 *
 */
public class TFonctionsDB extends TableDB {

	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TFonctionsDB.class );  
	private String queryDeleteProjet;
	private PreparedStatement dbPrepDeleteProjet;
	private PreparedStatement dbPrepArchive;
	private String queryArchive;
	
	private PreparedStatement dbPrepArchiveDuProjet;
	private String queryArchiveDuProjet;
	
	private PreparedStatement dbPrepUpdateReport;
	private String queryReport;
	
	private PreparedStatement dbPrepUpdateReportProjet;
	private String queryReportProjet;
	
	private PreparedStatement dbPrepComplexite;
	private String queryComplexite;
	
	private PreparedStatement dbPrepComplexiteSimple;
	private String queryComplexiteSimple;
	
	protected String queryUpdateSelection;
	protected PreparedStatement dbPrepUpdateSelection;
	
	int index=0;

	







	/**
	 * @param nameTable
	 * @param nameID
	 * @param driver
	 * @throws ExceptionTraitementSQL 
	 */
	public TFonctionsDB() throws ExceptionTraitementSQL {
		super("TFONCTIONS","IDFONC");
		log.traceEntry("TFonctionsModel()") ;
		
		prepareDeleteProjet();
		prepareArchive();
		prepareArchiveDuProjet();
		prepareReport() ;
		prepareComplexite();
		prepareComplexiteSimple();
		prepareUpdateSelection();
		
		log.traceExit("TFonctionsModel()") ;
	}
	
	public void prepareUpdateSelection() throws ExceptionTraitementSQL {

		// Prépare requete modification
		queryUpdateSelection="UPDATE TFONCTIONS SET FOCUSED = ? WHERE IDFONC=?";
		this.dbPrepUpdateSelection = prepareQuery(queryUpdateSelection);
	}



	/* (non-Javadoc)
	 * @see model.TableModel#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() throws ExceptionTraitementSQL {
	
		log.traceEntry("prepareUpdate()");
		// Prépare la modification de tprojets.libelle.
		// System.out.println("Appel de prepareUpdate dans TFonctionsModel");
		queryUpdate="UPDATE TFONCTIONS SET LIBELLE = ?, IDCOMPLEXITE=?, ESTIMATION=?,IDPARETO=?,IDEISENHOWER=?, IDMOTIVATION=? WHERE IDFONC=?";
		this.dbPrepUpdate = prepareQuery(queryUpdate);
		
		log.traceExit("prepareUpdate()") ;

	}
	
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	private void prepareComplexiteSimple() throws ExceptionTraitementSQL {

		log.traceEntry("prepareComplexiteSimple()");
		//On crée l'objet avec la requête en paramètre
		queryComplexiteSimple ="UPDATE TFONCTIONS SET IDCOMPLEXITE = ?, ESTIMATION= ? WHERE IDFONC=?";
	    this.dbPrepComplexiteSimple = prepareQuery(queryComplexiteSimple);
	    log.traceExit("prepareComplexiteSimple()") ;
		
	}
	public void prepareComplexite() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareComplexite()");
		// Prépare la modification de tprojets.libelle.
		// System.out.println("Appel de prepareUpdate dans TFonctionsModel");
		queryComplexite="UPDATE TFONCTIONS SET IDCOMPLEXITE=?, ESTIMATION=?,IDPARETO=?,IDEISENHOWER=?, IDMOTIVATION=? WHERE IDFONC=?";
		this.dbPrepComplexite = prepareQuery(queryComplexite);
		
		log.traceExit("prepareComplexite()") ;

	}
	
	/** Préparation d'une requete de type delete paramétrée pour suppression des fonctions d'un projet
	 * @throws ExceptionTraitementSQL
	 */
	private void prepareDeleteProjet() throws ExceptionTraitementSQL {
		log.traceEntry("prepareDeleteProjet()");
		
		//On crée l'objet avec la requête en paramètre
		
		queryDeleteProjet ="DELETE  FROM TFONCTIONS WHERE IDPROJET=?";
	    this.dbPrepDeleteProjet = prepareQuery(queryDeleteProjet);
	    log.traceExit("prepareDeleteProjet()") ;
	    
	}



	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	private void prepareReport() throws ExceptionTraitementSQL {
		log.traceEntry("prepareReport()");
		
		//On crée l'objet avec la requête en paramètre
		
		queryReport ="UPDATE TFONCTIONS SET REPORT=?, FOCUSED=false, ARCHIVE=false WHERE IDFONC=?";
	    this.dbPrepUpdateReport = prepareQuery(queryReport);
	    
	    
		queryReportProjet ="UPDATE TFONCTIONS SET REPORT=?, FOCUSED=false, ARCHIVE=false WHERE IDPROJET=?";
	    this.dbPrepUpdateReportProjet = prepareQuery(queryReportProjet);
	    
	    log.traceExit("prepareReport()") ;
		
	}
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	private void prepareArchive() throws ExceptionTraitementSQL {
		log.traceEntry("prepareArchive()");
		
		//On crée l'objet avec la requête en paramètre

		queryArchive ="UPDATE TFONCTIONS SET ARCHIVE=?, FOCUSED=false, REPORT=false WHERE IDFONC=?";
	    this.dbPrepArchive = prepareQuery(queryArchive);
	    log.traceExit("prepareArchive()") ;
		
	}


	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	private void prepareArchiveDuProjet() throws ExceptionTraitementSQL {
		log.traceEntry("prepareArchiveDuProjet()");
		
		//On crée l'objet avec la requête en paramètre

		queryArchiveDuProjet ="UPDATE TFONCTIONS SET ARCHIVE=?, FOCUSED=false, REPORT=false WHERE IDPROJET=?";
	    this.dbPrepArchiveDuProjet = prepareQuery(queryArchiveDuProjet);
	    log.traceExit("prepareArchiveDuProjet()") ;
		
	}


	
	/* (non-Javadoc)
	 * @see model.TableModel#prepareInsert()
	 */
	@Override
	public void prepareInsert() throws ExceptionTraitementSQL {
		
		log.traceEntry("prepareInsert()");
		
		//On crée l'objet avec la requête en paramètre
		// System.out.println("Appel de prepareInsert dans TFonctionModel");
		queryInsert ="INSERT INTO TFONCTIONS "
				+ "("
				+ "IDFONC,LIBELLE,IDPROJET,"
				+ "IDPARETO, IDEISENHOWER,IDCOMPLEXITE,"
				+ "ESTIMATION,IDMOTIVATION, REPORT,"
				+ "FOCUSED,ARCHIVE,IDDELEGUER) "
				+ "values (?,?,?,?,?,?,?,?,false,false,false,null)";
		this.dbPrepInsert = prepareQuery(queryInsert);
		log.traceExit("prepareInsert()") ;

	}



	/**
	 * Charge la liste des fonctions avec les données de la base
	 * @param lesFonctionsDuProjets
	 * @param idProj
	 * @throws ExceptionTraitementSQL 
	 */
	
//	public void getList(ListModelFonction lesFonctionsDuProjets, int idProj) throws ExceptionTraitementSQL {
//		
//		setQueryList("SELECT IDFONC,LIBELLE,SELECTED FROM TFONCTIONS WHERE ARCHIVE=false AND IDPROJET = " + Integer.toString(idProj) + " ORDER BY LIBELLE");
//		lesFonctionsDuProjets.clear();
//		setIndex(0);
//		setList(lesFonctionsDuProjets);
//		int size;	
//		loadListGenerique();
//		
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see model.DBTable#initListObject()
//	 */
//	@Override
//	protected void initListObject() {
//		// Rien à faire, déjà fait !
//		
//	}	
	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) {
//		index++
//		lesFonctionsDuProjets.addElement(new DataInfoFonction(result.getInt("IDFONC"),result.getString("LIBELLE"),idProj,result.getBoolean("SELECTED"),getIndex()));
	}
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/*
	public void getList(ListModelFonction lesFonctionsDuProjets, int idProj) throws ExceptionTraitementSQL {

		log.traceEntry("getList(ListModelFonction lesFonctionsDuProjets, int idProj = "+idProj+")") ;
		
		//TODO A GENERALISER
		ResultSet result =null;
		String query ="SELECT IDFONC,LIBELLE,FOCUSED,IDPARETO,IDEISENHOWER,IDCOMPLEXITE,ESTIMATION FROM TFONCTIONS WHERE ARCHIVE=false AND REPORT=false AND IDDELEGUER IS NULL AND IDPROJET = " + Integer.toString(idProj) + " ORDER BY LIBELLE";
		
		lesFonctionsDuProjets.clear();
		int index=0;
		int size;
		
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			
			result = DB.dbStatement.executeQuery(query);
			
			while(result.next()){ 
				
				lesFonctionsDuProjets.addElement(
						new DataInfoFonction(
								result.getInt("IDFONC"),
								result.getString("LIBELLE"),
								idProj,
								result.getBoolean("FOCUSED"),
								result.getInt("IDPARETO"),
								result.getInt("IDEISENHOWER"),
								result.getInt("IDCOMPLEXITE"),
								result.getDouble("ESTIMATION"),
								index++));
			}

			size=lesFonctionsDuProjets.size();
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);
			size=-1;
			throw new ExceptionTraitementSQL(e,query);
			
		}
		
		log.traceExit("getList - size = " + size) ;
		
	}
	
*/






	protected Vector<DataInfoFonction> getList(int idProj, String query) throws ExceptionTraitementSQL {

		log.traceEntry("Vector<DataInfoFonction> getList(int idProj="+idProj+")");
		
		//TODO A GENERALISER AUSSI
		
		Vector<DataInfoFonction> listeFonctions = new Vector<DataInfoFonction>();
		
		ResultSet result =null;
		int index=0;
		
		
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			
			result = DB.dbStatement.executeQuery(query);
	
			log.trace("getList : Rq OK " +query);
			
			while(result.next()){ 
				
				listeFonctions.add(
						new DataInfoFonction(
								result.getInt("IDFONC"),
								result.getString("LIBELLE"),
								idProj,
								result.getBoolean("FOCUSED"),
								result.getInt("IDPARETO"),
								result.getInt("IDEISENHOWER"),
								result.getInt("IDCOMPLEXITE"),
								result.getDouble("ESTIMATION"),
								result.getInt("IDMOTIVATION"),
								index++));

			}

			log.traceExit("getList(int idProj) OK size = "+listeFonctions.size()) ;
			return listeFonctions;
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);

			log.traceExit("getList(int idProj) KO ") ;
			throw new ExceptionTraitementSQL(e,query);
		
		}
		
		
	}
	
	
	public Vector<DataInfoFonction> getList(int id) throws ExceptionTraitementSQL {
		
		String query ="SELECT IDFONC,LIBELLE,FOCUSED,IDPARETO,IDEISENHOWER,IDCOMPLEXITE,ESTIMATION,IDMOTIVATION "
				+ "FROM TFONCTIONS "
				+ "WHERE ARCHIVE=false AND REPORT=false AND IDPROJET = " + Integer.toString(id) 
				+ " ORDER BY FOCUSED DESC, IDPARETO ASC, IDEISENHOWER ASC, IDMOTIVATION ASC, LIBELLE ASC";

		return getList(id,query);
	}
	/**
	 * @param id
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector<DataInfoFonction> getListArchive(int id) throws ExceptionTraitementSQL {
		String query ="SELECT IDFONC,LIBELLE,FOCUSED,IDPARETO,IDEISENHOWER,IDCOMPLEXITE,ESTIMATION,IDMOTIVATION "
				+ "FROM TFONCTIONS "
				+ "WHERE ARCHIVE=true  AND IDPROJET = " + Integer.toString(id) 
				+ " ORDER BY LIBELLE ASC";

		return getList(id,query);
	}

	/**
	 * @param id
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector<DataInfoFonction> getListReport(int id) throws ExceptionTraitementSQL {

		String query ="SELECT IDFONC,LIBELLE,FOCUSED,IDPARETO,IDEISENHOWER,IDCOMPLEXITE,ESTIMATION,IDMOTIVATION "
				+ "FROM TFONCTIONS "
				+ "WHERE REPORT=true  AND IDPROJET = " + Integer.toString(id) 
				+ " ORDER BY FOCUSED DESC, IDPARETO ASC, IDEISENHOWER ASC, LIBELLE ASC";

		return getList(id,query);
	}

	
	/** Fonction suppression des fonctions d'un projet
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
	public void deleteProjet(int id) throws ExceptionTraitementSQL {
		
		log.traceEntry("deleteProjet(int id="+id+")");
		
		
		super.delete(dbPrepDeleteProjet, id);
		
		log.traceExit("deleteProjet(int id)") ;
		
	}

		/**
	 * @param nameFonc : nom de la fonction
	 * @param idProj : reference du projet
	 * @return id de la fonction
	 * @throws ExceptionTraitementSQL
	 */
	public int add(String nameFonc,int idProj,int idpareto, int ideisenhower, int idcomplexite, double estimation,int idmotivation) throws ExceptionTraitementSQL {

		log.traceEntry("add(String nameFonc="+nameFonc+",int idProj="+idProj+"int idpareto="+idpareto+", int ideisenhower="+ideisenhower+", int idcomplexite="+idcomplexite+", double estimation="+estimation+",int idmotivation="+idmotivation+")");
		
		//TODO A Généraliser
		
		//INSERT INTO TFONCTIONS (IDFONC,LIBELLE,IDPROJET,IDPARETO, IDEISENHOWER,IDCOMPLEXITE,ESTIMATION, REPORT,FOCUSED,ARCHIVE,IDDELEGUER) values (?,?,?,false,false,false,null)";
		//Ajouter une référence de fonction dans la table TFONCTIONS
		
		try 
		{
			
			//On paramètre notre requête préparée
			dbPrepInsert.setInt(1, this.getNextID());
			dbPrepInsert.setString(2, nameFonc);
			dbPrepInsert.setInt(3, idProj);
			dbPrepInsert.setInt(4, idpareto);
			dbPrepInsert.setInt(5, ideisenhower);
			dbPrepInsert.setInt(6, idcomplexite);
			dbPrepInsert.setDouble(7, estimation);
			dbPrepInsert.setInt(8, idmotivation);
			
   			//On exécute
			dbPrepInsert.executeUpdate();	
			
			int ret = getCurrentID();
		
			log.traceExit("add(String nameFonc,int idProj) OK => id="+ret) ;
			return ret;
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+dbPrepInsert.toString());
			log.fatal("printStackTrace " , e);
			
			log.traceExit("add(String nameFonc,int idProj) KO ") ;
			throw new ExceptionTraitementSQL(e,dbPrepInsert.toString());
			
			
		}
	}

	/**
	 * @param id
	 * @param text
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateFonction(int id, String nameFonction,int idcomplexite, double estimation, int idpareto, int ideisenhower,int idmotivation) throws ExceptionTraitementSQL {
		log.traceEntry("updateFonction(int id="+id+", String nameFonction="+nameFonction+",int idcomplexite="+idcomplexite+", "
				+ "double estimation="+estimation+", int idpareto="+idpareto
			    + ", int ideisenhower="+ideisenhower+",int idmotivation="+idmotivation+")");
		try 
		{
			//queryUpdate="UPDATE TFONCTIONS SET LIBELLE = ?, IDCOMPLEXITE=?, ESTIMATION=?,IDPARETO=?,IDEISENHOWER=? WHERE IDFONC=?";
			//On paramètre notre requête préparée
			dbPrepUpdate.setString(1, nameFonction);
			dbPrepUpdate.setInt(2, idcomplexite);
			dbPrepUpdate.setDouble(3, estimation);
			dbPrepUpdate.setInt(4, idpareto);
			dbPrepUpdate.setInt(5, ideisenhower);
			dbPrepUpdate.setInt(6, idmotivation);
			dbPrepUpdate.setInt(7, id);

			//On exécute
			dbPrepUpdate.executeUpdate();	
			// System.out.println("Rq sur tfonction OK "+dbPrepUpdate.toString()  );



		} catch (SQLException e) {
			// System.out.println("Rq sur tfonction KO "+dbPrepUpdate.toString()  );
			
			log.fatal("Rq KO "+dbPrepUpdate.toString());
			log.fatal("printStackTrace " , e);
			
			throw new ExceptionTraitementSQL(e,dbPrepUpdate.toString());

		}
		log.traceExit("updateFonction()") ;
	}



	/**
	 * @param id
	 * @throws ExceptionTraitementSQL 
	 */
	public void archiveFonction(int id,boolean value) throws ExceptionTraitementSQL {
		log.traceEntry("archiveFonction(int id="+id+")");
		
		updateBoolean(dbPrepArchive, id, value);
		
		log.traceExit("archiveFonction()") ;
		
	}

	/**
	 * @param id
	 * @param value
	 * @throws ExceptionTraitementSQL 
	 */
	public void archiveFonctionDuProjet(int id, boolean value) throws ExceptionTraitementSQL {
		log.traceEntry("archiveFonctionDuProjet(int id="+id+")");
		
		updateBoolean(dbPrepArchiveDuProjet, id, value);
		
		log.traceExit("archiveFonctionDuProjet()") ;
		
	}

	/**
	 * @param id
	 * @param idComplexite
	 * @param estimation
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateComplexite(int id, int idComplexite, Double estimation,int idPareto,int idEisenhower,int idmotivation) throws ExceptionTraitementSQL {
		
		log.traceEntry("updateComplexite(int id="+id+", int idComplexite="+idComplexite+", Double estimation="+estimation+", int idmotivation="+idmotivation+") ");		
		//"UPDATE TFONCTIONS SET IDCOMPLEXITE=?, ESTIMATION=?,IDPARETO=?,IDEISENHOWER=?, IDMOTIVATION=? WHERE IDFONC=?
		try 
		{
			//On paramètre notre requête préparée
			int n=1;
			dbPrepComplexite.setInt(n, idComplexite);
			dbPrepComplexite.setDouble(++n, estimation);
			dbPrepComplexite.setInt(++n, idPareto);
			dbPrepComplexite.setInt(++n, idEisenhower);
			dbPrepComplexite.setInt(++n, idmotivation);
			
			dbPrepComplexite.setInt(++n, id);
			
			//On exécute
			dbPrepComplexite.executeUpdate();	
		} catch (SQLException e) {
			// System.out.println("Rq sur tfonction KO "+dbPrepDelete.toString()  );
			log.fatal("Rq KO "+dbPrepComplexite.toString());
			log.fatal("printStackTrace " , e);
			throw new ExceptionTraitementSQL(e,dbPrepComplexite.toString());

		}
		log.traceExit("updateComplexite()") ;
	}

public void updateComplexiteSimple(int id, int idComplexite, Double estimation) throws ExceptionTraitementSQL {
		
		log.traceEntry("updateComplexiteSimple(int id="+id+", int idComplexite="+idComplexite+", Double estimation="+estimation+") ");		
		//"UPDATE TFONCTIONS SET COMPLEXITE = ?, ESTIMATION= ? WHERE IDFONC=?"
		try 
		{
			//On paramètre notre requête préparée
			int n=1;
			dbPrepComplexiteSimple.setInt(n, idComplexite);
			dbPrepComplexiteSimple.setDouble(++n, estimation);
			dbPrepComplexiteSimple.setInt(++n, id);
			
			//On exécute
			dbPrepComplexiteSimple.executeUpdate();	
		} catch (SQLException e) {
			// System.out.println("Rq sur tfonction KO "+dbPrepDelete.toString()  );
			log.fatal("Rq KO "+dbPrepComplexiteSimple.toString());
			log.fatal("printStackTrace " , e);
			throw new ExceptionTraitementSQL(e,dbPrepComplexiteSimple.toString());

		}
		log.traceExit("updateComplexiteSimple()") ;
	}




	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		// Pas utilisé
		
	}






	/**
	 * @param id
	 * @param b
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateSelectionByDefault(int id, boolean value) throws ExceptionTraitementSQL {
		log.traceEntry("updateSelectionByDefault(int id="+id+", boolean value="+value+")");

		updateBoolean(dbPrepUpdateSelection, id, value);

		log.traceExit("updateSelectionByDefault()");

		
	}

	/**
	 * @param id
	 * @param b
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateReport(int id, boolean value) throws ExceptionTraitementSQL {
		log.traceEntry("updateReport(int id="+id+", boolean value="+value+")");

		updateBoolean(dbPrepUpdateReport, id, value);

		log.traceExit("updateReport()");

		
	}

	/** Reporter toutes les fonctions du projet
	 * @param id
	 * @param value
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateReportProjet(int id, boolean value) throws ExceptionTraitementSQL {
		

		log.traceEntry("updateReportProjet(int id="+id+", boolean value="+value+")");

		updateBoolean(dbPrepUpdateReportProjet, id, value);

		log.traceExit("updateReportProjet()");

	}












		
	}


