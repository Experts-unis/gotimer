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
public class TTypologieDB extends TableDB {

	private Vector<DataInfoTypologie> list;
	/**
	 * @param nameTable
	 * @param nameID
	 * @throws ExceptionTraitementSQL
	 */
	public TTypologieDB()
			throws ExceptionTraitementSQL {
		super("TTYPOLOGIE", "IDTYPO");
		
		list=getList();
		if (list.size()==0){
			// Initialisation de la table ttypologie
			//add(String name,boolean solo, boolean perso)
			add("Solo JAVA SE",true,false);
			add("Solo PHP",true,false);
			add("Equipe JAVA SE",false,false);
			add("Administratif",true,true);
			
		}
		
	}

	/* (non-Javadoc)
	 * @see model.DBTable#prepareInsert()
	 */
	@Override
	public void prepareInsert() throws ExceptionTraitementSQL {
		log.traceEntry("prepareInsert()");
		
		//On crée l'objet avec la requête en paramètre
		// System.out.println("Appel de prepareInsert dans TFonctionModel");
		queryInsert ="INSERT INTO TTYPOLOGIE (IDTYPO,LIBELLE,SOLO,PERSO) values (?,?,?,?)";
		this.dbPrepInsert = prepareQuery(queryInsert);
		log.traceExit("prepareInsert()") ;

	}

	/* (non-Javadoc)
	 * @see model.DBTable#prepareUpdate()
	 */
	@Override
	public void prepareUpdate() throws ExceptionTraitementSQL {
		log.traceEntry("prepareUpdate()");
		queryUpdate = "UPDATE TTYPOLOGIE SET LIBELLE = ?, SOLO=?, PERSO=? WHERE IDTYPO=?";
		this.dbPrepUpdate = prepareQuery(queryUpdate);
		log.traceExit("prepareUpdate()");

	}

	/* (non-Javadoc)
	 * @see model.DBTable#addNewElement(java.sql.ResultSet)
	 */
	@Override
	protected void addNewElementInAVectorList(ResultSet result) throws SQLException {
		
		
		list.add(new DataInfoTypologie(result.getInt("IDTYPO"), result.getString("LIBELLE"), 
				result.getBoolean("SOLO"), result.getBoolean("PERSO")) );

	}

	/* (non-Javadoc)
	 * @see model.DBTable#initListObject()
	 */
	@Override
	protected void initListObject() {
		
		if (list==null) list = new Vector<DataInfoTypologie>();
		else list.removeAllElements();
		setQueryList("SELECT IDTYPO,LIBELLE,SOLO,PERSO FROM TTYPOLOGIE"); 

	}
	
	public Vector<DataInfoTypologie> getList() throws ExceptionTraitementSQL
	{
		super.loadListGenerique(); 
		return list;
		
	}
	public int add(String name,boolean solo, boolean perso) throws ExceptionTraitementSQL {

		log.traceEntry("add(String name="+name+",boolean solo="+solo+", boolean perso="+perso+")");
		
		//TODO A Généraliser
		
		//INSERT INTO TTYPOLOGIE (IDTYPO,LIBELLE,SOLO,PERSO, IDCOEFF)
		
		try 
		{
			
			//On paramètre notre requête préparée
			dbPrepInsert.setInt(1, this.getNextID());
			dbPrepInsert.setString(2, name);
			dbPrepInsert.setBoolean(3, solo);
			dbPrepInsert.setBoolean(4, perso);
		
			
   			//On exécute
			dbPrepInsert.executeUpdate();	
			
			int ret = getCurrentID();
		
			log.traceExit("add() OK => id="+ret) ;
			return ret;
		} catch (SQLException e) {
			
			log.fatal("Rq KO "+dbPrepInsert.toString());
			log.fatal("printStackTrace " , e);
			
			log.traceExit("add() KO ") ;
			throw new ExceptionTraitementSQL(e,dbPrepInsert.toString());
			
			
		}
	}

	/**
	 * @param id
	 * @param name
	 * @param solo
	 * @param perso
	 * @throws ExceptionTraitementSQL 
	 */
	public void update(int id, String name, boolean solo, boolean perso) throws ExceptionTraitementSQL {
		
	log.traceEntry("update(int id="+id+", String name="+name+", boolean solo="+solo+", boolean perso="+perso+")");
		
		//queryUpdate="UPDATE TPROJETS SET LIBELLE = ?,IDPARETO=?, IDOBJECTIF=?,IDTYPOLOGIE=?,IDMOTIVATION=? WHERE IDPROJET=?";
		
		try 
		{
			//On paramètre notre requête préparée
			dbPrepUpdate.setString(1, name);
			dbPrepUpdate.setBoolean(2, solo);
			dbPrepUpdate.setBoolean(3, perso);
			dbPrepUpdate.setInt(4, id);
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
}
