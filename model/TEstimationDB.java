/**
 * 
 */
package model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
public class TEstimationDB extends TableDB {

	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TEstimationDB.class );


	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public TEstimationDB() throws ExceptionTraitementSQL {
		super("TESTIMATIONS","ID");
		
		log.traceEntry("TEstimationDB()") ;
		
		log.traceExit("TEstimationDB()") ;
	}

	/* (non-Javadoc)
	 * @see model.TableModel#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() throws ExceptionTraitementSQL {

		log.traceEntry("prepareUpdate()");
		// Pr�pare la modification de tprojets.libelle.
		// System.out.println("Appel de prepareUpdate dans TFonctionsModel");
		queryUpdate="UPDATE TESTIMATIONS SET  RESTEAFAIRE=? WHERE ID=?";
		this.dbPrepUpdate = prepareQuery(queryUpdate);
		

		
		log.traceExit("prepareUpdate()") ;

	}
	/* (non-Javadoc)
	 * @see model.TableModel#prepareInsert()
	 */
	@Override
	public void prepareInsert() throws ExceptionTraitementSQL {

		log.traceEntry();
		
		//On cr�e l'objet avec la requ�te en param�tre
		// System.out.println("Appel de prepareInsert dans TFonctionModel");
		queryInsert ="INSERT INTO TESTIMATIONS (ID,IDPROJ,IDFONC,RESTEAFAIRE,DT,ARCHIVE) values (?,?,?,?,?,false)";
		this.dbPrepInsert = prepareQuery(queryInsert);
		log.traceExit() ;

	}
	
	public int add(int idproj,int idfonc,  double delai,double charge, double resteafaire) throws ExceptionTraitementSQL {
		log.traceEntry("add( double charge="+charge+", double resteafaire="+resteafaire+")");
		
		//Ajouter une nouvelle estimation de reste � faire dans la table TESTIMATIONS
		
		try 
		{
			Timestamp date = new Timestamp(new Date().getTime());
			int n=1;
			//INSERT INTO TESTIMATIONS (ID,IDPROJ,IDFONC,RESTEAFAIRE,DT,ARCHIVE) values (?,?,?,?,?,false)
			dbPrepInsert.setInt(n++, this.getNextID());
			dbPrepInsert.setInt(n++, idproj);
			dbPrepInsert.setInt(n++, idfonc);
			dbPrepInsert.setDouble(n++, resteafaire);
			dbPrepInsert.setTimestamp(n++, date );
   			
			//On ex�cute
			dbPrepInsert.executeUpdate();	
			
			int ret = getCurrentID();
		
			log.traceExit("add(...) OK => id="+ret) ;
			return ret;
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+dbPrepInsert.toString());
			log.fatal("printStackTrace " , e);
			
			log.traceExit("add(...) KO => -1") ;
			throw new ExceptionTraitementSQL(e,dbPrepInsert.toString());
			
			
			
		}
	}
	
	/**
	 * @param id
	 * @param estimation
	 * @param charge
	 * @param resteafaire
	 * @throws ExceptionTraitementSQL 
	 */
	public void update(int id,  double resteafaire) throws ExceptionTraitementSQL {

		log.traceEntry("Update(int id="+id+",  double resteafaire="+resteafaire+")");
		try 
		{
			//On param�tre notre requ�te pr�par�e
			
			dbPrepUpdate.setDouble(1, resteafaire);
			dbPrepUpdate.setInt(2, id);
			
			//On ex�cute
			dbPrepUpdate.executeUpdate();	

		} catch (SQLException e) {
			
			log.fatal("Rq KO "+dbPrepUpdate.toString());
			log.fatal("printStackTrace " , e);
			throw new ExceptionTraitementSQL(e,dbPrepUpdate.toString());

		}
		log.traceExit("Update()") ;
	}

	

	
	/**
	 * @param idProj
	 * @return un objet contenant les informations sur les estimations
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector<DataInfoEstimation> getList(int idProj) throws ExceptionTraitementSQL {

		log.traceEntry("Vector<DataInfoEstimation> getList(int idProj="+idProj+")");
		
		
		Vector<DataInfoEstimation> listeFonctions = new Vector<DataInfoEstimation>();
		
		
		
		ResultSet result =null;
		String query ="SELECT ID, IDPROJET, IDFONC, LIBELLE as LIBFONC,  INITIALE, "
				+ "IDCOMP, IDPARETO, IDEISENHOWER, IDMOTIVATION, RESTEAFAIRE, OLDRESTEAFAIRE "
				+ "FROM  VESTIMATIONSDETAILS "  
				+ "WHERE  ARCHIVE=false AND REPORT=false AND  IDPROJET= " 
				+ Integer.toString(idProj) + "  ORDER BY  IDPARETO ASC, IDEISENHOWER ASC, IDMOTIVATION ASC, LIBFONC ASC";
		
		
		DataInfoEstimation element;
		
		//L'objet ResultSet contient le r�sultat de la requ�te SQL
		try {
			
			result = DB.dbStatement.executeQuery(query);
	
			log.trace("getList : Rq OK " +query);
			
			while(result.next()){ 
				
				element = new DataInfoEstimation();
				
				element.setId(result.getInt("ID"));
				element.setIdproj(result.getInt("IDPROJET"));
				element.setIdfonc(result.getInt("IDFONC"));
				element.setLibelle(result.getString("LIBFONC"));
				element.setEstimationInit(result.getDouble("INITIALE"));
				element.setIdComplexite(result.getInt("IDCOMP"));
				element.setIdPareto(result.getInt("IDPARETO"));
				element.setIdEisenhower(result.getInt("IDEISENHOWER"));
				element.setIdMotivation(result.getInt("IDMOTIVATION"));
				
				
			//	element.setDt(result.getDate("DT"));
				element.setResteafaire(result.getDouble("RESTEAFAIRE"));
				element.setOldresteafaire(result.getDouble("OLDRESTEAFAIRE"));
				element.setUnite(1); // Les donn�es sont toujours exprim�es en heure dans la base
//				element.calculEstimation();
				
				
				
				
				
			 	listeFonctions.add(element);

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
	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		// pas utilis� pour le moment
		
	}
	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) {
		// pas utilis�
		
	}


	/* (non-Javadoc)
	 * @see model.DBTable#prepareDelete()
	 */
	@Override
	public void prepareDelete() {
		// pas utilis�
		
	}

	



	
}
