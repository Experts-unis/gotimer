package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import controleur.ExceptionTraitementSQL;



public class TRefLibelleDB extends TableDB{

	static org.apache.logging.log4j.Logger log = LogManager . getLogger ( TRefLibelleDB.class );  
	protected String query;
	private Vector<DataRefLibelle> liste;
	



	public TRefLibelleDB(String nameTable) throws ExceptionTraitementSQL {
		super(nameTable, "ID");
		
		log.traceEntry("TRefLibelleDB(String nameTable="+nameTable+")");
		liste = null;
		
		query ="SELECT ID, LIBELLE FROM " + nameTable;
		
		log.traceExit("TRefLibelleDB()");
		
	}

	/* (non-Javadoc)
	 * @see model.TableModel#prepareInsert()
	 */
	@Override
	public void prepareInsert() throws ExceptionTraitementSQL {
		// Prépare requete insertion
		queryInsert ="INSERT INTO " + nameTable + " (ID,LIBELLE) VALUES (?,?)";
		this.dbPrepInsert = prepareQuery(queryInsert);
				
	}
	
	

	/* (non-Javadoc)
	 * @see model.TableModelDB#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() throws ExceptionTraitementSQL {
		// Prépare requete modification
		queryUpdate="UPDATE " + nameTable + " SET LIBELLE = ? WHERE ID=?";
		this.dbPrepUpdate = prepareQuery(queryUpdate);
		
	}



	/** Add : Ajouter un libelle
	 * @param libelle
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public DataRefLibelle add(String libelle) throws ExceptionTraitementSQL
	{
		log.traceEntry("DataRefLibelle add(String libelle="+libelle+")");

		try 
		{
			log.trace("Rq sur  "+nameTable + " : " + queryInsert );
			//On paramètre notre requête préparée
			dbPrepInsert.setInt(1, this.getNextID());
			dbPrepInsert.setString(2, libelle);
		
   			//On exécute
			dbPrepInsert.executeUpdate();	
	
			DataRefLibelle element = new DataRefLibelle (getCurrentID(),libelle);
			log.traceExit("DataRefLibelle add() OK "+element.id );
			return element;
		} catch (SQLException e) {
			log.fatal("Rq KO "+dbPrepInsert.toString() );
			log.fatal("printStackTrace " , e);
			log.traceExit("add(String nameProjet) KO") ;
			throw new ExceptionTraitementSQL(e,dbPrepInsert.toString());
		}
			
		
	}
	
	/** Modifier un libelle
	 * @param id
	 * @param libelle
	 * @throws ExceptionTraitementSQL 
	 */
	public void maj(int id, String libelle) throws ExceptionTraitementSQL
	{
		log.traceEntry("void maj(int id="+id+", String libelle="+libelle+")");
		try 
		{
			log.trace("Rq sur "+nameTable+" OK "+queryUpdate );
			//On paramètre notre requête préparée
			
			dbPrepUpdate.setString(1, libelle);
			dbPrepUpdate.setInt(2, id);
			
			//On exécute
			dbPrepUpdate.executeUpdate();	
			log.traceExit ("Rq  OK "+dbPrepUpdate.toString() );

		} catch (SQLException e) {
			log.fatal("Rq KO "+dbPrepUpdate.toString() );
			log.fatal("printStackTrace " , e);
			log.traceExit("maj() KO") ;
			throw new ExceptionTraitementSQL(e,dbPrepUpdate.toString());
		}
		
		
	}
	

	
	/**
	 * @return
	 */
	public Vector<DataRefLibelle>  getList() {

		// Créer une liste d'info à partir de la table tcomptes de la base de données;
		//JComboBox<DataRefLibelle> cmb = new JComboBox<DataRefLibelle>();
		
		
		liste =new Vector<DataRefLibelle>();
		
		ResultSet result =null;
		
		
	
		//L'objet ResultSet contient le résultat de la requête SQL
		try {
			result = DB.dbStatement.executeQuery(query);
			
			while(result.next()){ 
				liste.addElement(new DataRefLibelle(result.getInt("ID"),result.getString("LIBELLE")));
			}

		} catch (SQLException e) {
			
			System.out.println("Rq KO "+query );
			e.printStackTrace();
			return null;
		}
		return liste ;
	}
	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) throws SQLException {
		liste.addElement(new DataRefLibelle(result.getInt("ID"),result.getString("LIBELLE")));
		
	}
	
	/**
	 * @param id : id dans la table
	 * @return le libelle correspondant à l'id
	 */
	public String search(int id){
		
		String libelle ="";
		
		
		if (liste == null )  getList() ;
		
	
		for (DataRefLibelle element : liste){
			if (element.getId()==id){
				libelle = element.getLibelle();
				break;
			}
		}
		
		return libelle;
		
		
	}

	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		// Pas utilisé
		
	}



}
