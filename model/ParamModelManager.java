/**
 * 
 */
package model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import controleur.ExceptionInitBase;
import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
public class ParamModelManager extends ModelManager {

	protected TPeriodeDB tPeriodes;
	private TVacationsDB tVacations;
	/**
	 * @throws SQLException
	 */
	public ParamModelManager() throws ExceptionTraitementSQL,ExceptionInitBase {
		tPeriodes = new TPeriodeDB();
		tVacations = new TVacationsDB();
	}

	/**
	 * @param value
	 * @throws ExceptionTraitementSQL 
	 */
	public void addActivite(DataInfoPeriodeActivite value) throws ExceptionTraitementSQL {

		int id = tPeriodes.add(value.getDebut(), value.getFin(), value.getDureeSQL());
		value.setId(id);
		
	}

	/**
	 * @param element
	 * @throws ExceptionTraitementSQL 
	 */
	public void suppPresence(DataInfoPeriodeActivite element) throws ExceptionTraitementSQL {
		if (element.id!=-1)
			tPeriodes.delete(element.id);
		
	}

	/**
	 * @param value
	 * @throws ExceptionTraitementSQL 
	 */
	public void addVacation(DataInfoVacation value) throws ExceptionTraitementSQL {
		
		Timestamp debut = value.getSQLDebut();
		Timestamp fin = value.getSQLFin();
		int id = tVacations.add (debut, fin);
		value.setId(id);
		
	}

	/**
	 * @param element
	 * @throws ExceptionTraitementSQL 
	 */
	public void suppVacation(DataInfoVacation element) throws ExceptionTraitementSQL {
		if (element.id!=-1)
			tVacations.delete(element.id);
		
	}

	/**
	 * @return 
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public Vector<DataInfoVacation> loadVacation() throws ExceptionTraitementSQL {
		
		return tVacations.getList();
		
	}

	/**Charge le tableau des présences
	 * @return
	 * @throws ExceptionTraitementSQL 
	 */
	public Vector<DataInfoPeriodeActivite> loadPresence() throws ExceptionTraitementSQL {
		
		return tPeriodes.getList();
	}

}
