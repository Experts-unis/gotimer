/**
 * 
 */
package model;

import javax.swing.DefaultListModel;
import javax.swing.JList;


import controleur.ExceptionTraitementSQL;



/**
 * @author test
 *
 */
public class ListModelProjet extends DefaultListModel<DataInfoProjet> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * @param controlManager
	 * @throws Exception 
	 */
	public ListModelProjet() throws Exception {
		
		
		
	
		
	}
	/**
	 * 
	 */
	public void refresh(JList <DataInfoProjet> list) {
		fireContentsChanged(list,0,size());
		
	}
	/**
	 * @throws ExceptionTraitementSQL 
	 * 
	 */
	public void reload() throws ExceptionTraitementSQL {
		
		removeAllElements();

		
		
	}

}
