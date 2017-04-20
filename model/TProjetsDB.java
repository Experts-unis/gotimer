package model;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;



import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;
 


public class TProjetsDB extends TableDB {
	
	protected String queryUpdateClos;
	protected PreparedStatement dbPrepUpdateClos;
	
	protected String queryUpdateSelection;
	protected PreparedStatement dbPrepUpdateSelection;
	private String queryUpdateReport;
	private PreparedStatement dbPrepUpdateReport;
	
	private Vector<Integer> listeIndexProjet;
	
	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TProjetsDB.class );  
	
	/**
	 * @throws ExceptionTraitementSQL
	 */
	public TProjetsDB() throws ExceptionTraitementSQL {
		super("TPROJETS", "IDPROJET");

		log.traceEntry("TProjetsModel()") ;
		listeIndexProjet=null;
		prepareUpdateClos();
		prepareUpdateSelection();
		prepareUpdateReport();
		
		log.traceExit("TProjetsModel()") ;
	}

	/* (non-Javadoc)
	 * @see model.TableModel#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() throws ExceptionTraitementSQL {

		// Prépare requete modification
		queryUpdate="UPDATE TPROJETS SET LIBELLE = ?,IDPARETO=?, IDOBJECTIF=?,IDTYPOLOGIE=?,IDMOTIVATION=?, IDCOEFF=?, DATESOUHAITEE=? WHERE IDPROJET=?";
		this.dbPrepUpdate =prepareQuery(queryUpdate);
	}
	
	
	public void prepareUpdateSelection() throws ExceptionTraitementSQL {

		// Prépare requete modification
		queryUpdateSelection="UPDATE TPROJETS SET FOCUSED = ? WHERE IDPROJET=?";
		this.dbPrepUpdateSelection = prepareQuery(queryUpdateSelection);
	}
	
	public void prepareUpdateReport() throws ExceptionTraitementSQL {

		// Prépare requete modification
		queryUpdateReport="UPDATE TPROJETS SET REPORT = ?, FOCUSED=false, ARCHIVE=false WHERE IDPROJET=?";
		this.dbPrepUpdateReport = prepareQuery(queryUpdateReport);
	}
	public void prepareUpdateClos() throws ExceptionTraitementSQL {

		// Prépare requete modification
		queryUpdateClos="UPDATE TPROJETS SET ARCHIVE = ?, FOCUSED=false, REPORT=false WHERE IDPROJET=?";
		this.dbPrepUpdateClos = prepareQuery(queryUpdateClos);
	}


	/* (non-Javadoc)
	 * @see model.TableModel#prepareInsert()
	 */
	@Override
	public void prepareInsert() throws ExceptionTraitementSQL {

		// Prépare requete insertion
		queryInsert ="INSERT INTO TPROJETS (IDPROJET,LIBELLE, IDPARETO, IDOBJECTIF,IDTYPOLOGIE,IDMOTIVATION, IDCOEFF, DATESOUHAITEE,FOCUSED,REPORT,ARCHIVE) "
				+ "values (?,?,?,?,?,?,?,?,false,false,false)";
		this.dbPrepInsert = prepareQuery(queryInsert);
	}

	public void updateSelectionByDefault(int id, boolean value) throws ExceptionTraitementSQL {
		
		log.traceEntry("updateSelectionByDefault(int id="+id+", boolean value="+value+")");
		
		updateBoolean(dbPrepUpdateSelection, id, value);

		log.traceExit("updateSelectionByDefault()");

	}

	
	public void updateReport(int id, boolean value) throws ExceptionTraitementSQL {
		
		log.traceEntry("updateReport(int id="+id+", boolean value="+value+")");
		
		updateBoolean(dbPrepUpdateReport, id, value);

		log.traceExit("updateReport()");

	}
	
	public void updateArchive(int id, boolean value) throws ExceptionTraitementSQL {
		
		log.traceEntry("updateArchive(int id="+id+", boolean value="+value+")");
		
		updateBoolean(dbPrepUpdateClos, id, value);

		log.traceExit("updateArchive()");

	}
	/**
	 * @return
	 * @throws ExceptionTraitementSQL
	 */
	private Vector<DataInfoProjet> getList(String query) throws ExceptionTraitementSQL {
		
		log.traceEntry("Vector<DataInfoProjet> getList())");
		
		ResultSet result =null;
		//String query ="SELECT IDPROJET,LIBELLE,FOCUSED,IDPARETO,IDOBJECTIF,IDTYPOLOGIE,IDMOTIVATION FROM TPROJETS WHERE ARCHIVE = false and REPORT=false  ORDER BY LIBELLE";
		Vector<DataInfoProjet> lesProjets;
		
		if (listeIndexProjet == null)
			listeIndexProjet = new Vector<Integer>();
		else
			listeIndexProjet.clear();
		
		
		lesProjets = new Vector<DataInfoProjet>();
		
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);
			// System.out.println("Rq OK" +query);
			log.trace("getList : Rq OK " +query);
			lesProjets.clear();
			int index=0;
			while(result.next()){ 

				
				
				listeIndexProjet.addElement(result.getInt("IDPROJET"));
				
				lesProjets.addElement(new DataInfoProjet(
						result.getInt("IDPROJET"),
						result.getString("LIBELLE"),
						result.getBoolean("FOCUSED"),
						result.getInt("IDPARETO"),
						result.getInt("IDOBJECTIF"),
						result.getInt("IDTYPOLOGIE"),result.getInt("IDMOTIVATION"),result.getInt("IDCOEFF"),
						result.getDate("DATESOUHAITEE"),index++));
			}

			
			log.traceExit("Vector<DataInfoProjet> getList() OK lesprojets.size = "+lesProjets.size()) ;
			return lesProjets;
			
		} catch (SQLException e) {


			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);
			
			log.traceExit("Vector<DataInfoProjet> getList() KO") ;
			throw new ExceptionTraitementSQL(e,query);
		}
		
		

	}
	
	public Vector<DataInfoProjet> getList() throws ExceptionTraitementSQL {
	
		
		return getList("SELECT IDPROJET,LIBELLE,FOCUSED,IDPARETO,IDOBJECTIF,IDTYPOLOGIE,IDMOTIVATION, IDCOEFF, DATESOUHAITEE "
				+ " FROM TPROJETS WHERE ARCHIVE = false AND REPORT = false  "
				+ "ORDER BY  FOCUSED DESC , IDOBJECTIF ASC, IDPARETO ASC, IDMOTIVATION ASC, LIBELLE ASC");
	}
	

	
	/**
	 * @param lesProjets 
	 * @param listeProjet
	 * @return
	 * @throws ExceptionTraitementSQL
	 */
	public void getEstimationsProjets(Vector<DataInfoProjet> lesProjets) throws ExceptionTraitementSQL {


		log.traceEntry("void getEstimationsProjets(Vector<DataInfoProjet> lesProjets)");

		ResultSet result =null;

		String query ="SELECT IDPROJET , LIBPROJET, TERMINER, ESTIMATIONTOTAL , RESTEAFAIRETOTAL , ESTIMRAFTOTAL"  
				+ " FROM vestimationsprojets";



		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);

			log.trace("getList : Rq OK " +query);

			int index;
			while(result.next()){ 


				//				public DataInfoEstimationProjet(int idprojet, String libelle,
				//						boolean terminer, double estimationtotale, double resteafairetotal,
				//						double oldraftotal) {


				int idprojet = result.getInt("IDPROJET");
				System.out.println("getEstimationsProjets IDPROJET = "+idprojet); 
				index = listeIndexProjet.indexOf(idprojet);
				System.out.println("getEstimationsProjets INDEX = "+index); 
				
				DataInfoProjet element = null;
				if (index!=-1) 
					element = lesProjets.get(index);



				if (element == null || index == -1){
					System.out.println(" ERREUR idprojet  "+idprojet+" index "+index);
					log.fatal("ERREUR Element projet manquant "+idprojet+ "  index = "+index);

					log.traceExit("getEstimationsProjets(Vector<DataInfoProjet> lesProjets) KO") ;
					throw new ExceptionTraitementSQL(null,"Element projet manquant");
				}else{

					System.out.println("element projet id "+element.getId()+" id estim "+idprojet+" index "+index);	

					element.setEstimation(	
							result.getBoolean("TERMINER"),
							result.getDouble("ESTIMATIONTOTAL"),
							result.getDouble("RESTEAFAIRETOTAL"),
							result.getDouble("ESTIMRAFTOTAL"));
				}


			}


			log.traceExit("void getEstimationsProjets(Vector<DataInfoProjet> lesProjets)") ;

		} catch (SQLException e) {


			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);

			log.traceExit("Vector<DataInfoProjet> getList() KO") ;
			throw new ExceptionTraitementSQL(e,query);
		}

	}

	/**
	 * @param lesProjets
	 */
	public void getConsommationProjets(Vector<DataInfoProjet> lesProjets)  throws ExceptionTraitementSQL {
		log.traceEntry("void getConsommationProjets(Vector<DataInfoProjet> lesProjets)");

		ResultSet result =null;

		String query ="SELECT IDPROJET,  DEBUT, CHARGE FROM vsuiviconsoprojets";



		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);

			log.trace("getList : Rq OK " +query);

			int index;
			while(result.next()){ 


				int idprojet = result.getInt("IDPROJET");
				System.out.println("getConsommationProjets IDPROJET = "+idprojet); 
				index = listeIndexProjet.indexOf(idprojet);
				System.out.println("getConsommationProjets INDEX = "+index); 
				DataInfoProjet element = lesProjets.get(index);



				if (element == null){
					System.out.println(" ERREUR id estim "+idprojet+" index "+index);
					log.fatal("ERREUR Element projet manquant "+idprojet+ "  index = "+index);

					log.traceExit("getEstimationsProjets(Vector<DataInfoProjet> lesProjets) KO") ;
					throw new ExceptionTraitementSQL(null,"Element projet manquant");
				}else{

					System.out.println("element projet id "+element.getId()+" id estim "+idprojet+" index "+index);	

					element.setConsommation(	
							result.getDate("DEBUT"),
							result.getDouble("CHARGE"));
				}


			}


			log.traceExit("void getEstimationsProjets(Vector<DataInfoProjet> lesProjets)") ;

		} catch (SQLException e) {


			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);

			log.traceExit("Vector<DataInfoProjet> getList() KO") ;
			throw new ExceptionTraitementSQL(e,query);
		}
		
		
	}
	/**
	 * @return
	 */
	public Vector<DataInfoProjet> getListArchive() throws ExceptionTraitementSQL {
		
		return getList("SELECT DISTINCT P.IDPROJET,P.LIBELLE,P.FOCUSED,P.IDPARETO,P.IDOBJECTIF,P.IDTYPOLOGIE,P.IDMOTIVATION, P.IDCOEFF,DATESOUHAITEE "
				+ "	 FROM TFONCTIONS F right join TPROJETS p on (p.IDPROJET=f.IDPROJET) "
				+ " WHERE f.ARCHIVE = true OR p.ARCHIVE = true "
				+ " ORDER BY LIBELLE ASC ");
	}
	
	/**
	 * @return
	 */
	public Vector<DataInfoProjet> getListReport() throws ExceptionTraitementSQL {
		
		return getList("SELECT DISTINCT P.IDPROJET,P.LIBELLE,P.FOCUSED,P.IDPARETO,P.IDOBJECTIF,P.IDTYPOLOGIE,P.IDMOTIVATION, P.IDCOEFF,DATESOUHAITEE "
				+ "	 FROM TFONCTIONS F right join TPROJETS p on (p.IDPROJET=f.IDPROJET) "
				+ " WHERE f.REPORT = true OR p.REPORT = true  "
				+ "ORDER BY FOCUSED DESC , IDOBJECTIF ASC, IDPARETO ASC, IDMOTIVATION ASC, LIBELLE ASC");
		 
		 

	}
//	public void getList(JComboBox<DataInfoProjet> cmb) {
//
//		log.traceEntry("getList(JComboBox<DataInfoProjet> cmb)");
//		ResultSet result =null;
//		String query ="SELECT IDPROJET,LIBELLE,SELECTED FROM TPROJETS WHERE ARCHIVE = false  ORDER BY LIBELLE";
//		
//		//L'objet ResultSet contient le résultat de la requête SQL
//		try {
//			result = DB.dbStatement.executeQuery(query);
//			
//			int index=0;
//			int indexSelected=0;
//			DataInfoProjet d;
//			while(result.next()){ 
//
//				cmb.addItem(d=new DataInfoProjet(result.getInt("IDPROJET"),result.getString("LIBELLE"),result.getBoolean("SELECTED"),index++));
//				if (d.isSelected()) indexSelected=index-1;
//			}
//			
//			cmb.setSelectedIndex(indexSelected);
//			
//
//		} catch (SQLException e) {
//
//			log.fatal("Rq KO "+query);
//			log.fatal("printStackTrace " , e);
//		}
//		log.traceExit("getList(JComboBox<DataInfoProjet> cmb)") ;
//		
//		
//	}

	public int add(String nameProjet,int idpareto, int idobjectif,int idtypologie,int idmotivation,int idcoeff,Date datesouhaitee) throws ExceptionTraitementSQL {
		
		log.traceEntry("add(String nameProjet="+nameProjet+")");
		//Ajouter une référence de projet dans la table TPROJETS
		
		try 
		{
			
			//On paramètre notre requête préparée
			dbPrepInsert.setInt(1, this.getNextID());
			dbPrepInsert.setString(2, nameProjet);
			dbPrepInsert.setInt(3, idpareto);
			dbPrepInsert.setInt(4, idobjectif);
			dbPrepInsert.setInt(5, idtypologie);
			dbPrepInsert.setInt(6, idmotivation);
			dbPrepInsert.setInt(7, idcoeff);
			dbPrepInsert.setDate(8, datesouhaitee);
			
   			//On exécute
			dbPrepInsert.executeUpdate();	
			
			
			int ret = getCurrentID();
			log.traceExit("add(String nameProjet) OK id => "+ret) ;

			return ret;
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+dbPrepInsert.toString() );
			log.fatal("printStackTrace " , e);
			log.traceExit("add(String nameProjet) KO") ;
			throw new ExceptionTraitementSQL(e,dbPrepInsert.toString());
		}
		
	}
	

	
	
	/**
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public int isSelectProjetExist() throws ExceptionTraitementSQL {
	
		log.traceEntry("isSelectProjetExist() ");
		int retour =-1;
		
		ResultSet result =null;
		String query ="SELECT IDPROJET FROM TPROJETS WHERE FOCUSED = true";
		
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);
			
			if (result.next()) {
				retour = result.getInt("IDPROJET");				
			}
			
			log.traceExit("isSelectProjetExist() retour = "+retour) ;
			return retour;

		} catch (SQLException e) {

			log.fatal("Rq KO "+query);
			log.fatal("printStackTrace " , e);

			log.traceExit("isSelectProjetExist() KO") ;

			throw new ExceptionTraitementSQL(e,query);
		}		

	}


	/**
	 * @param id
	 * @param text
	 * @throws ExceptionTraitementSQL 
	 */
	public void updateProjet(int id, String text,int idpareto, int idobjectif,int idtypologie,int idmotivation,int idcoeff,Date datesouhaitee) throws ExceptionTraitementSQL {

		log.traceEntry("updateProjet(int id="+id+", String text="+text+",int idpareto="+idpareto
				+", int idobjectif="+idobjectif+",int idtypologie="+idtypologie
				+",int idmotivation="+idmotivation+",int idcoeff="+idcoeff
				+"Date datesouhaitee = "+datesouhaitee.toString()+")");
		
		//queryUpdate 
		
		try 
		{
			//On paramètre notre requête préparée
			dbPrepUpdate.setString(1, text);
			dbPrepUpdate.setInt(2, idpareto);
			dbPrepUpdate.setInt(3, idobjectif);
			dbPrepUpdate.setInt(4, idtypologie);
			dbPrepUpdate.setInt(5, idmotivation);
			dbPrepUpdate.setInt(6, idcoeff);
			dbPrepUpdate.setDate(7, datesouhaitee);
			dbPrepUpdate.setInt(8, id);
			

			//On exécute
			dbPrepUpdate.executeUpdate();	

			log.traceExit("updateProjet() OK") ;
		} catch (SQLException e) {
			log.fatal("Rq KO "+dbPrepUpdate.toString() );
			log.fatal("printStackTrace " , e);
			log.traceExit("updateProjet() KO") ;
			throw new ExceptionTraitementSQL(e,dbPrepUpdate.toString() );
		}
		
	}

	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) {
		// pas utilisé pour le moment
		
	}

	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		//pas utilisé
		
	}












}
