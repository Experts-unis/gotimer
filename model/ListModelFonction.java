/**
 * 
 */
package model;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * @author test
 *
 */
public class ListModelFonction extends DefaultListModel<DataInfoFonction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public void refresh(JList <DataInfoFonction> list) {
		fireContentsChanged(list,0,size());
		
	}

}
