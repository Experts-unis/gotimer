/**
 * 
 */
package model;

import java.sql.SQLException;


import javax.swing.JComboBox;

import controleur.ExceptionTraitementSQL;

/**
 * @author test
 *
 */
public class ComplexiteModelManager extends ModelManager {
	
	

	/**
	 * @throws SQLException
	 */
	public ComplexiteModelManager() throws Exception {
		
	}

	/**
	 * @param cmbComplexite
	 */
	public void loadComplexiteInCombo(JComboBox<DataRefLibelle> cmbComplexite) {
		
		log.traceEntry("loadComplexiteInCombo");

		copyBundleInComboBoxSimple(cmbComplexite, tComplexite.getData());
		
		cmbComplexite.setSelectedIndex(0);

		log.traceExit("loadComplexiteInCombo");
		
		
	}

	



	/**
	 * @param element
	 * @throws ExceptionTraitementSQL 
	 */
	public void modifEstimation(DataInfoFonction element) throws ExceptionTraitementSQL {
		tFonctions.updateComplexite(element.getId(),
				element.getIdComplexite(),
				element.getEstimation(),
				element.getIdpareto(),
				element.getIdeisenhower(),
				element.getIdmotivation());
		
	}








}
